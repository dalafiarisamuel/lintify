package com.lint.lintify.detectors

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Severity.ERROR
import com.intellij.psi.PsiNamedElement
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.isTopLevelKtOrJavaMember
import org.jetbrains.uast.*
import org.jetbrains.uast.kotlin.declarations.KotlinUMethod


class NamingPatternDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes() =
        listOf<Class<out UElement>>(UVariable::class.java, UMethod::class.java, UClass::class.java)

    override fun createUastHandler(context: JavaContext) =
        NamingPatternHandler(
            context
        )


    companion object {

        val ISSUE = Issue.create(
            id = "NamingPattern",
            briefDescription = "Names should be well named.",
            explanation =
            """Sometimes there is more than one reasonable way to convert an English phrase into camel case, such as when acronyms or unusual constructs like "IPv6" or "iOS" 
                are present. XML HTTP request becomes XmlHttpRequest. XMLHTTPRequest would be incorrect.""".trimMargin(),
            category = CORRECTNESS,
            priority = 9,
            severity = ERROR,
            implementation = Implementation(
                NamingPatternDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    class NamingPatternHandler(private val context: JavaContext) : UElementHandler() {
        override fun visitVariable(node: UVariable) {
            val isConstant = node.isFinal && node.isStatic
            val isEnumConstant = node is UEnumConstant

            if (!isConstant && !isEnumConstant) {
                process(node, node)
            }
        }

        override fun visitMethod(node: UMethod) {
            process(node, node)
        }

        override fun visitClass(node: UClass) {
            process(node, node)
        }

        private fun process(scope: UElement, declaration: PsiNamedElement) {
            val isKotlinTopLevelOrMember =
                scope is KotlinUMethod && ((scope.sourcePsi as? KtProperty)?.isMember == true || scope.sourcePsi?.isTopLevelKtOrJavaMember() == true)

            if (declaration.name?.isDefinedCamelCase() == false && !isKotlinTopLevelOrMember) {
                context.report(
                    ISSUE,
                    scope,
                    context.getNameLocation(scope),
                    "${declaration.name} is not named in defined camel case."
                )
            }
        }
    }
}

private fun String.isDefinedCamelCase(): Boolean {
    val toCharArray = toCharArray()
    return toCharArray
        .mapIndexed { index, current -> current to toCharArray.getOrNull(index + 1) }
        .none { it.first.isUpperCase() && it.second?.isUpperCase() ?: false }
}
package com.lint.lintify.detectors

import com.android.SdkConstants.*
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import com.android.tools.lint.detector.api.Severity.WARNING
import com.android.tools.lint.detector.api.XmlContext
import com.lint.lintify.attributes
import org.w3c.dom.Element

class SuperfluousNameSpaceDetector : LayoutDetector() {

    override fun getApplicableElements(): MutableList<String> = ALL

    override fun visitElement(context: XmlContext, element: Element) {
        if (element.parentNode.parentNode != null) {
            element.attributes()
                .filter { attribute -> possibleUris.any { attribute.toString().contains(it) } }
                .forEach {
                    val fix = fix()
                        .name("Remove namespace")
                        .replace()
                        .range(context.getLocation(it))
                        .all()
                        .build()

                    context.report(
                        ISSUE,
                        it,
                        context.getLocation(it),
                        "This name space is already declared and hence not needed.",
                        fix
                    )
                }
        }
    }


    companion object {
        private val possibleUris = setOf(ANDROID_URI, TOOLS_URI, AUTO_URI, AAPT_URI)

        val ISSUE = Issue.create(
            "SuperfluousNameSpace",
            "Flags namespaces that are already declared.",
            "Re-declaring a namespace is unnecessary and hence can be just removed.",
            CORRECTNESS, 6, WARNING,
            Implementation(SuperfluousNameSpaceDetector::class.java, RESOURCE_FILE_SCOPE)
        )
    }
}
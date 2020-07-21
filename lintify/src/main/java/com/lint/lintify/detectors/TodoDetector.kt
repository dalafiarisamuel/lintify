package com.lint.lintify.detectors

import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Scope.*
import com.android.tools.lint.detector.api.Severity.WARNING
import org.jetbrains.uast.UClass
import org.w3c.dom.Document
import java.util.*
import java.util.regex.Pattern

private const val COMMENT = "TODO:"
private val pattern = Pattern.compile("[\\t]*$COMMENT.*")


class TodoDetector : Detector(), Detector.UastScanner, Detector.GradleScanner,
    Detector.OtherFileScanner, Detector.XmlScanner {
    override fun getApplicableUastTypes() = listOf(UClass::class.java)

    companion object {
        val ISSUE = Issue.create(
            id = "Todo",
            briefDescription = "Marks todos in any given file.",
            explanation = "TODO in any given file should be resolved.",
            category = CORRECTNESS,
            priority = 9,
            severity = WARNING,
            implementation = Implementation(
                TodoDetector::class.java,
                EnumSet.of(JAVA_FILE, GRADLE_FILE, PROGUARD_FILE, MANIFEST, RESOURCE_FILE),
                EnumSet.of(JAVA_FILE, GRADLE_FILE, PROGUARD_FILE, MANIFEST, RESOURCE_FILE)
            )
        )
    }

    override fun visitDocument(context: XmlContext, document: Document) {
        // Needs to be overridden but we we'll do the work in afterCheckFile.
    }

    override fun afterCheckFile(context: Context) {
        val source = context.getContents().toString()
        val matcher = pattern.matcher(source)

        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()

            val location = Location.create(context.file, source, start, end)
            context.report(ISSUE, location, "Contains todo.")
        }
    }
}
package com.lint.lintify.detectors

import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.XmlContext
import com.lint.lintify.hasToolsNamespace
import org.w3c.dom.Element
import java.util.*


abstract class SuperfluousDeclarationDetector(
    private val issue: Issue,
    private val message: String,
    private val applicableSuperfluousAttributes: Collection<String>
) : LayoutDetector() {

    override fun getApplicableElements(): MutableList<String> = ALL

    override fun visitElement(context: XmlContext, element: Element) {
        val attributes = (0 until element.attributes.length)
            .asSequence()
            .map { element.attributes.item(it) }
            .filterNot { it.hasToolsNamespace() }
            .filter { applicableSuperfluousAttributes.contains(it.localName) }
            .map { it.nodeValue }
            .toList()

        if (attributes.size == applicableSuperfluousAttributes.size && HashSet<String>(attributes).size == 1) {
            // Replacing with a Lint fix isn't possible yet. https://issuetracker.google.com/issues/74599279
            context.report(issue, element, context.getLocation(element), message)
        }
    }
}
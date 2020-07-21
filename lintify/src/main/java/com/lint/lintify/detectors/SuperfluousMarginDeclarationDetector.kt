package com.lint.lintify.detectors

import com.android.SdkConstants.*
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import com.android.tools.lint.detector.api.Severity.WARNING


class SuperfluousMarginDeclarationDetector : SuperfluousDeclarationDetector(
    applicableSuperfluousAttributes = listOf(
        ATTR_LAYOUT_MARGIN_TOP,
        ATTR_LAYOUT_MARGIN_BOTTOM,
        ATTR_LAYOUT_MARGIN_START,
        ATTR_LAYOUT_MARGIN_END
    ),
    issue = ISSUE,
    message = "Should be using layout_margin instead."
) {
    companion object {

        val ISSUE = Issue.create(
            "SuperfluousMarginDeclaration",
            "Flags margin declarations that can be simplified.",
            "Instead of using start-, end-, bottom- and top margins, layout_margin can be used.",
            CORRECTNESS, 9, WARNING,
            Implementation(SuperfluousMarginDeclarationDetector::class.java, RESOURCE_FILE_SCOPE)
        )
    }
}
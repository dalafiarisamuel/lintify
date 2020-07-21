package com.lint.lintify

import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.lint.lintify.detectors.*

class IssueRegistry : com.android.tools.lint.client.api.IssueRegistry() {

    override val api: Int = CURRENT_API

    override val issues: List<Issue>
        get() = listOf(
            LogDetector.ISSUE,
            NamingPatternDetector.ISSUE,
            ShouldUseStaticImportDetector.ISSUE,
            TodoDetector.ISSUE,
            SuperfluousMarginDeclarationDetector.ISSUE,
            SuperfluousNameSpaceDetector.ISSUE
        )

}
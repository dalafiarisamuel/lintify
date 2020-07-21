package com.lint.lintify.detectors

import com.android.ddmlib.Log


open class LintLog {

    companion object {

        private const val CHUNK_SIZE = 4076

        fun d(buildType: String, tag: String, message: String) {
            when (buildType) {

                "debug", "staging" -> simpleLog(
                    tag,
                    message
                )
            }

        }

        fun w(buildType: String, tag: String, message: String) {
            when (buildType) {

                "debug", "staging" -> Log.w(tag, message)
            }

        }

        fun e(buildType: String, tag: String, message: String) {
            when (buildType) {

                "debug", "staging" -> Log.e(tag, message)
            }

        }

        fun e(buildType: String, tag: String, message: Throwable) {
            when (buildType) {

                "debug", "staging" -> Log.e(tag, message)
            }

        }

        fun v(buildType: String, tag: String, message: String) {
            when (buildType) {

                "debug", "staging" -> Log.v(tag, message)
            }

        }

        private fun simpleLog(tag: String, message: String) {

            var offset = 0
            while (offset + CHUNK_SIZE <= message.length) {
                Log.d(
                    tag,
                    message.substring(offset, CHUNK_SIZE.let { offset += it; offset })
                )
            }
            if (offset < message.length) {
                Log.d(tag, message.substring(offset))
            }
        }


    }
}
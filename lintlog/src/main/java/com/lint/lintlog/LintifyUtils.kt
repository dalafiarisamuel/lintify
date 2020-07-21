package com.lint.lintlog

import android.util.Log


open class LintLog private constructor() {

    companion object {

        private const val CHUNK_SIZE = 4076

        @JvmStatic
        fun d(buildType: String, tag: String, message: String) {
            when (buildType) {

                "debug", "staging" -> simpleLog(
                    tag,
                    message
                )
            }

        }

        @JvmStatic
        fun w(buildType: String, tag: String, message: String) {
            when (buildType) {

                "debug", "staging" -> Log.w(tag, message)
            }

        }

        @JvmStatic
        fun e(buildType: String, tag: String, message: String) {
            when (buildType) {

                "debug", "staging" -> Log.e(tag, message)
            }

        }


        @JvmStatic
        fun v(buildType: String, tag: String, message: String) {
            when (buildType) {

                "debug", "staging" -> Log.v(tag, message)
            }

        }

        @JvmStatic
        fun wtf(buildType: String, tag: String, message: Throwable) {

            when (buildType) {

                "debug", "staging" -> Log.wtf(tag, message)
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
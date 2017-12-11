package com.jaloveast1k.thisorthat.repository.data

import com.jaloveast1k.thisorthat.BuildConfig

/**
 * Created by ulsab on 09.12.2017.
 */

class Consts {
    object General {
        val NUMBER_OF_QUESTIONS = if (BuildConfig.DEBUG) 5 else 20

        const val DB_NAME = "this-or-that-database"
        const val PREFS_NAME = "prefs_name"
        const val CLIENT = "android"

        const val ANSWER_NO = "no"
        const val ANSWER_UP = "left"
        const val ANSWER_DOWN = "right"
        const val ANSWER_SKIP = "skip"

        const val QUESTION_MODERATED = 1
    }

    object SharedPrefs {
        const val TOKEN = "token"
        const val USER_ID = "user_id"
    }

    object UI {
        const val TITLE_TEXT_SIZE_TO_WIDTH = 18f
        const val TEXT_SIZE_TO_WIDTH = 20f
        const val SMALL_TEXT_SIZE_TO_WIDTH = 22f
        const val VERY_SMALL_TEXT_SIZE_TO_WIDTH = 25f
    }
}
package com.jaloveast1k.thisorthat.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jaloveast1k.thisorthat.repository.data.Question


@Database(entities = arrayOf(Question::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}
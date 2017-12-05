package com.jaloveast1k.thisorthat.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jaloveast1k.thisorthat.repository.data.User


@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
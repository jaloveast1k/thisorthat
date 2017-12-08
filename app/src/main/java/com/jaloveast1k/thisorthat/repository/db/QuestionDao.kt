package com.jaloveast1k.thisorthat.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.jaloveast1k.thisorthat.repository.data.Question
import io.reactivex.Single

@Dao
interface QuestionDao {
    @Query("SELECT * FROM quests")
    fun getQuestions(): Single<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<Question>)
}
package com.jaloveast1k.thisorthat.repository.db

import android.arch.persistence.room.*
import com.jaloveast1k.thisorthat.repository.data.Consts.General.ANSWER_NO
import com.jaloveast1k.thisorthat.repository.data.Question
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface QuestionDao {
    @Query("SELECT * FROM quests")
    fun getQuestions(): Single<List<Question>>

    @Query("SELECT * FROM quests WHERE answer = \"" + ANSWER_NO + "\"")
    fun getNotAnsweredQuestions(): Single<List<Question>>

    @Delete
    fun deleteQuestions(questions: List<Question>): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<Question>)
}
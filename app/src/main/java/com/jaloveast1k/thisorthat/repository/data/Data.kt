package com.jaloveast1k.thisorthat.repository.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

val client = "android"

/*
TODO: В старой версии id задавался руками и обозначался как _id, так же все поля были типа String, порядок сохранен
 */
@Entity(tableName = "quests")
data class Question(
        @ColumnInfo(name = "text_up")
        val textUp: String,
        @ColumnInfo(name = "text_down")
        val textDown: String,
        @ColumnInfo(name = "vote_up")
        val voteUp: Int,
        @ColumnInfo(name = "vote_down")
        val voteDown: Int,
        @ColumnInfo(name = "answer")
        val answer: Int,
        @PrimaryKey
        @ColumnInfo(name = "quest_id")
        val serverId: Int,
        @ColumnInfo(name = "quest_type")
        val type: Int,
        @ColumnInfo(name = "quest_approve")
        val approve: Int,
        @ColumnInfo(name = "moderate")
        val moderate: Int)
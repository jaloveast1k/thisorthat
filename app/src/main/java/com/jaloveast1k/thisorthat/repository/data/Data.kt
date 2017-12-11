package com.jaloveast1k.thisorthat.repository.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*
TODO: В старой версии id задавался руками и обозначался как _id, так же все поля были типа String, порядок сохранен
 */
@Entity(tableName = "quests")
data class Question(
        @SerializedName("left_text")
        @ColumnInfo(name = "text_up")
        var textUp: String,
        @SerializedName("right_text")
        @ColumnInfo(name = "text_down")
        var textDown: String,
        @SerializedName("left_vote")
        @ColumnInfo(name = "vote_up")
        var voteUp: Int,
        @SerializedName("right_vote")
        @ColumnInfo(name = "vote_down")
        var voteDown: Int,
        @ColumnInfo(name = "answer")
        var answer: String = Consts.General.ANSWER_NO,
        @PrimaryKey
        @ColumnInfo(name = "quest_id")
        var serverId: Int,
        @ColumnInfo(name = "quest_type")
        var type: Int,
        @ColumnInfo(name = "quest_approve")
        var approve: Int,
        @SerializedName("moderate")
        @ColumnInfo(name = "moderate")
        var moderate: Int) {
    fun percentDown(): Int = if (voteDown + voteUp == 0) 50 else voteDown * 100 / (voteDown + voteUp)
    fun percentUp(): Int = 100 - percentDown()
}
package com.example.roomex.model.questions.room.entities

import androidx.room.*
import com.example.roomex.model.questions.entities.Question
import com.example.roomex.room.converters.QuestionAnswersConverter

@Entity(
    tableName = "questions",
    indices = [
        Index("title", unique = true)
    ]
)
@TypeConverters(QuestionAnswersConverter::class)
data class QuestionDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "question") val question: String,


    @ColumnInfo(name = "answers") val answers: List<String>,

    @ColumnInfo(name = "true_answer") val trueAnswer: String,
    @ColumnInfo(name = "question_owner") val questionOwner: Long
) {
    fun toQuestion(): Question = Question(
        id = id,
        title = title,
        question = question,
        answers = answers,
        trueAnswer = trueAnswer
    )
}
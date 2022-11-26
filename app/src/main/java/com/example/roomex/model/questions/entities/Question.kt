package com.example.roomex.model.questions.entities

import com.example.roomex.model.WrongAnswersSizeException
import com.example.roomex.model.WrongCustomQuestionException
import com.example.roomex.model.WrongTrueAnswerException

data class Question(
    val id: Long,
    val title: String,
    val question: String,
    val answers: List<String>,
    val trueAnswer: String
) {
    fun validateQuestion(){
        if (title.isBlank()) throw WrongCustomQuestionException()
        if (question.isBlank()) throw WrongCustomQuestionException()
        if (answers.size>4 || answers.size<2) throw WrongAnswersSizeException()
        if (trueAnswer.isBlank() || !answers.contains(trueAnswer)) throw WrongTrueAnswerException()
    }
}

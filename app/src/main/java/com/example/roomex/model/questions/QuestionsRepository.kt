package com.example.roomex.model.questions

import com.example.roomex.model.questions.entities.Question
import kotlinx.coroutines.flow.Flow

interface QuestionsRepository {

    suspend fun getCustomQuestions(): Flow<List<Question>>

    suspend fun updateCustomQuestion(question: Question)

    suspend fun getGeneralQuestions(): List<Question>

    suspend fun createCustomQuestion(question: Question)

    suspend fun deleteCustomQuestion(question: Question)

}
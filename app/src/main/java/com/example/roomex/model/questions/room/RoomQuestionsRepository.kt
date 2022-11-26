package com.example.roomex.model.questions.room

import com.example.roomex.model.accounts.AccountsRepository
import com.example.roomex.model.questions.QuestionsRepository
import com.example.roomex.model.questions.entities.Question
import com.example.roomex.model.questions.room.entities.QuestionDbEntity
import com.example.roomex.model.settings.AppSettings
import com.example.roomex.room.wrapSQLiteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RoomQuestionsRepository(
    private val accountsRepository: AccountsRepository,
    private val questionsDao: QuestionsDao,
    private val appSettings: AppSettings
) : QuestionsRepository {


    override suspend fun getCustomQuestions(): Flow<List<Question>> {
        return accountsRepository.getAccount()
            .flatMapLatest { account ->
                if (account == null) return@flatMapLatest flowOf(emptyList())
                queryCustomQuestions(account.id)
            }
    }

    override suspend fun updateCustomQuestion(question: Question) = wrapSQLiteException(Dispatchers.IO) {
        val accountId = appSettings.getCurrentAccountId()

        val newQuestion = QuestionDbEntity(
            id = question.id,
            title = question.title,
            question = question.question,
            answers = question.answers,
            trueAnswer = question.trueAnswer,
            questionOwner = accountId
        )

        updateCustomQuestionInDB(newQuestion)
        return@wrapSQLiteException
    }

    override suspend fun getGeneralQuestions(): List<Question> = wrapSQLiteException(Dispatchers.IO){
        questionsDao.getGeneralQuestions().map { it.toQuestion() }
    }

    override suspend fun createCustomQuestion(question: Question) = wrapSQLiteException(Dispatchers.IO){
        val accountId = appSettings.getCurrentAccountId()

        addCustomQuestionToDB(QuestionDbEntity(
            id = 0,
            title = question.title,
            question = question.question,
            answers = question.answers,
            trueAnswer = question.trueAnswer,
            questionOwner = accountId
        ))

        return@wrapSQLiteException
    }

    override suspend fun deleteCustomQuestion(question: Question) = wrapSQLiteException(Dispatchers.IO) {
        val accountId = appSettings.getCurrentAccountId()

        deleteCustomQuestionFromDB(QuestionDbEntity(
            id = question.id,
            title = question.title,
            question = question.question,
            answers = question.answers,
            trueAnswer = question.trueAnswer,
            questionOwner = accountId
        ))
    }

    private fun queryCustomQuestions(accountId: Long): Flow<List<Question>> {
        return questionsDao.findAllCustomQuestionByAccountId(accountId)
            .map { entities ->
                entities.map {
                    it.toQuestion()
                }
            }
    }

    private suspend fun updateCustomQuestionInDB(question: QuestionDbEntity) {
        questionsDao.updateCustomQuestion(question)
    }

    private suspend fun addCustomQuestionToDB(question: QuestionDbEntity) {
        questionsDao.createCustomQuestion(question)
    }

    private suspend fun deleteCustomQuestionFromDB(question: QuestionDbEntity) {
        questionsDao.deleteCustomQuestion(question)
    }
}
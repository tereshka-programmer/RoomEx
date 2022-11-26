package com.example.roomex.model.questions.room

import androidx.room.*
import com.example.roomex.model.accounts.room.entities.AccountDbEntity
import com.example.roomex.model.questions.room.entities.QuestionDbEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface QuestionsDao {

    @Query("SELECT * FROM questions WHERE question_owner = :accountId")
    fun findAllCustomQuestionByAccountId(accountId: Long): Flow<List<QuestionDbEntity>>

    @Update
    suspend fun updateCustomQuestion(question: QuestionDbEntity)

    @Query("SELECT * FROM questions WHERE question_owner = 9999")
    suspend fun getGeneralQuestions(): List<QuestionDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCustomQuestion(question: QuestionDbEntity)

    @Delete(entity = QuestionDbEntity::class)
    suspend fun deleteCustomQuestion(question: QuestionDbEntity)
}


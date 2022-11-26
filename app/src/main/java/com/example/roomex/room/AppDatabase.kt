package com.example.roomex.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomex.model.accounts.room.AccountsDao
import com.example.roomex.model.accounts.room.entities.AccountDbEntity
import com.example.roomex.model.questions.room.QuestionsDao
import com.example.roomex.model.questions.room.entities.QuestionDbEntity

@Database(
    version = 1,
    entities = [
        AccountDbEntity::class,
        QuestionDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getQuestionsDao(): QuestionsDao
}
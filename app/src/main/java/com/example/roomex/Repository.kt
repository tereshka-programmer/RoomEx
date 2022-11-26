package com.example.roomex

import android.content.Context
import androidx.room.Room
import com.example.roomex.model.accounts.AccountsRepository
import com.example.roomex.model.accounts.room.RoomAccountsRepository
import com.example.roomex.model.settings.AppSettings
import com.example.roomex.model.settings.SharedPreferencesAppSettings
import com.example.roomex.room.AppDatabase

object Repository {

    private lateinit var appContext: Context

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(appContext, AppDatabase::class.java, "database.db")
            .build()
    }

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    // --- repositories

    val accountsRepository: AccountsRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao(), appSettings)
    }

    fun init(context: Context) {
        appContext = context
    }

}
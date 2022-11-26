package com.example.roomex.model.accounts

import com.example.roomex.model.accounts.entities.Account
import com.example.roomex.model.accounts.entities.SignUpData
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    suspend fun isSignedIn(): Boolean

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(signUpData: SignUpData)

    suspend fun logout()

    suspend fun updateAccountUsername(newUsername: String)

    suspend fun getAccount(): Flow<Account?>
}
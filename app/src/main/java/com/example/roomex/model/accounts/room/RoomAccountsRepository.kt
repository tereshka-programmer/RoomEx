package com.example.roomex.model.accounts.room

import android.database.sqlite.SQLiteConstraintException
import com.example.roomex.model.AccountAlreadyExistsException
import com.example.roomex.model.AuthException
import com.example.roomex.model.EmptyFieldException
import com.example.roomex.model.Field
import com.example.roomex.model.accounts.AccountsRepository
import com.example.roomex.model.accounts.entities.Account
import com.example.roomex.model.accounts.entities.SignUpData
import com.example.roomex.model.accounts.room.entities.AccountDbEntity
import com.example.roomex.model.accounts.room.entities.AccountUpdateUsernameTuple
import com.example.roomex.model.settings.AppSettings
import com.example.roomex.room.wrapSQLiteException
import com.example.roomex.utils.AsyncLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class RoomAccountsRepository(
    private val accountsDao: AccountsDao,
    private val appSettings: AppSettings,
) : AccountsRepository {

    private val currentAccountIdFlow = AsyncLoader {
        MutableStateFlow(AccountId(appSettings.getCurrentAccountId()))
    }


    override suspend fun isSignedIn(): Boolean {
        return appSettings.getCurrentAccountId() != AppSettings.NO_ACCOUNT_ID
    }

    override suspend fun signIn(email: String, password: String) = wrapSQLiteException(Dispatchers.IO) {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)

        val accountId = findAccountIdByEmailAndPassword(email, password)
        appSettings.setCurrentAccountId(accountId)

        currentAccountIdFlow.get().value = AccountId(accountId)

        return@wrapSQLiteException
    }

    override suspend fun signUp(signUpData: SignUpData) = wrapSQLiteException(Dispatchers.IO) {
        signUpData.validate()
        createAccount(signUpData)
    }

    override suspend fun logout() {
        appSettings.setCurrentAccountId(AppSettings.NO_ACCOUNT_ID)
        currentAccountIdFlow.get().value = AccountId(AppSettings.NO_ACCOUNT_ID)
    }

    override suspend fun updateAccountUsername(newUsername: String) = wrapSQLiteException(Dispatchers.IO) {
        if (newUsername.isBlank()) throw EmptyFieldException(Field.Username)

        val accountId = appSettings.getCurrentAccountId()
        if (accountId != AppSettings.NO_ACCOUNT_ID) throw AuthException()

        updateUsernameForAccountId(accountId, newUsername)
        currentAccountIdFlow.get().value = AccountId(accountId)
        return@wrapSQLiteException
    }

    override suspend fun getAccount(): Flow<Account?> {
        return currentAccountIdFlow.get()
            .flatMapLatest { accountId ->
                if (accountId.value == AppSettings.NO_ACCOUNT_ID) {
                    flowOf(null)
                } else {
                    getAccountById(accountId.value)
                }
            }.flowOn(Dispatchers.IO)
    }

    private suspend fun createAccount(signUpData: SignUpData) {
        try {
            val entity = AccountDbEntity.fromSignUpData(signUpData)
            accountsDao.createAccount(entity)
        } catch (e: SQLiteConstraintException) {
            val appException = AccountAlreadyExistsException()
            appException.initCause(e)
            throw appException
        }
    }

    private suspend fun findAccountIdByEmailAndPassword(email: String, password: String): Long {
        val tuple = accountsDao.findByEmail(email) ?: throw AuthException()
        if (tuple.password != password) throw AuthException()
        return tuple.id
    }

    private fun getAccountById(accountId: Long): Flow<Account?> {
        return accountsDao.getById(accountId).map { it?.toAccount() }
    }

    private suspend fun updateUsernameForAccountId(accountId: Long, newUsername: String) {
        accountsDao.updateUserName(AccountUpdateUsernameTuple(accountId, newUsername))
    }

    private class AccountId(val value: Long)
}
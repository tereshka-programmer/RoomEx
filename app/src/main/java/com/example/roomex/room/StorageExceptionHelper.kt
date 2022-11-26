package com.example.roomex.room

import android.database.sqlite.SQLiteConstraintException
import com.example.roomex.model.StorageException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

/**
 * Converts any [SQLiteException] into in-app [StorageException]
 */

suspend fun <T> wrapSQLiteException(dispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> T):T {
    try {
        return withContext(dispatcher, block)
    } catch (e: SQLiteConstraintException) {
        val appException = StorageException()
        appException.initCause(e)
        throw appException
    }
}
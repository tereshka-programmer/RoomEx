package com.example.roomex.model.accounts.room.entities

import androidx.room.ColumnInfo

data class AccountSignInTuple(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "password") val password: String
)

data class AccountUpdateUsernameTuple(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "username") val username: String
)
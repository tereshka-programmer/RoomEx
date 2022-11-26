package com.example.roomex.model.accounts.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.roomex.model.accounts.entities.Account
import com.example.roomex.model.accounts.entities.SignUpData


@Entity(
    tableName = "accounts",
    indices = [
        Index("email", unique = true)
    ]
)
data class AccountDbEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "email", collate = ColumnInfo.NOCASE) val email: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String
) {
    fun toAccount(): Account = Account(
        id = id,
        email = email,
        username = username
    )

    companion object {
        fun fromSignUpData(signUpData: SignUpData) = AccountDbEntity(
            id = 0,
            email = signUpData.email,
            username = signUpData.username,
            password = signUpData.password
        )
    }
}
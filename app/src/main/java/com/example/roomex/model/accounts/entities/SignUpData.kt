package com.example.roomex.model.accounts.entities

import com.example.roomex.model.EmptyFieldException
import com.example.roomex.model.Field
import com.example.roomex.model.PasswordMismatchException

class SignUpData (
    val username: String,
    val email: String,
    val password: String,
    val repeatPassword: String
) {
    fun validate() {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (username.isBlank()) throw EmptyFieldException(Field.Username)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        if (repeatPassword != password) throw PasswordMismatchException()
    }
}
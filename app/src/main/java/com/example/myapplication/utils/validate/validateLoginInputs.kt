package com.example.myapplication.utils.validate

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.domain.model.login_model.LoginRequest

fun validateLoginInputs(
    email: String,
    password: String,
    setEmailError: (WrongVerify) -> Unit,
    setPasswordError: (WrongVerify) -> Unit,
    context: Context
): LoginRequest? {
    var hasError = false



    // Validate Email
    when {
        email.isBlank() -> {
            setEmailError(WrongVerify(true, context.getString(R.string.error_empty_email)))
            hasError = true
        }
        !isValidEmail(email) -> {
            setEmailError(WrongVerify(true, context.getString(R.string.error_invalid_email)))
            hasError = true
        }
        else -> setEmailError(WrongVerify(false, ""))
    }

    // Validate Password
    when {
        password.isBlank() -> {
            setPasswordError(WrongVerify(true, context.getString(R.string.error_empty_password)))
            hasError = true
        }
        password.length < 8 -> {
            setPasswordError(WrongVerify(true, context.getString(R.string.error_short_password)))
            hasError = true
        }
        !isStrongPassword(password) -> {
            setPasswordError(WrongVerify(true, context.getString(R.string.error_weak_password)))
            hasError = true
        }
        else -> setPasswordError(WrongVerify(false, ""))
    }

    return if (hasError) null else LoginRequest(
        email = email.trim().lowercase(),
        password = password
    )
}

private fun isValidEmail(email: String): Boolean {
    return email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
}

private fun isStrongPassword(password: String): Boolean {
    val hasLetter = password.any { it.isLetter() }
    val hasDigit = password.any { it.isDigit() }
    return hasLetter && hasDigit
}

package com.example.myapplication.utils.validate

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpRequest

fun validateResetPasswordInputs(
    verifyOTP: String,
    email: String,
    password: String,
    rePassword: String,
    setPasswordError: (WrongVerify) -> Unit,
    setRePasswordError: (WrongVerify) -> Unit,
    context: Context
): NewPasswordAndOtpRequest? {
    var hasError = false

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

    if (rePassword != password) {
        setRePasswordError(WrongVerify(true, context.getString(R.string.error_passwords_not_match)))
        hasError = true
    } else {
        setRePasswordError(WrongVerify(false, ""))
    }

    return if (hasError) null else NewPasswordAndOtpRequest(
        email = email.trim().lowercase(),
        otp = verifyOTP,
        newPassword = password
    )
}

private fun isStrongPassword(password: String): Boolean {
    val hasLetter = password.any { it.isLetter() }
    val hasDigit = password.any { it.isDigit() }
    return hasLetter && hasDigit
}

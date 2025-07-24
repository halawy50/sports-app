package com.example.myapplication.utils.validate

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.domain.model.generate_otp.GenerateOTPRequest

fun validateSendOTPInputs(
    email: String,
    setEmailError: (WrongVerify) -> Unit,
    context: Context
): GenerateOTPRequest? {
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

    return if (hasError) null else GenerateOTPRequest(
        email = email.trim().lowercase()
    )
}

private fun isValidEmail(email: String): Boolean {
    return email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
}

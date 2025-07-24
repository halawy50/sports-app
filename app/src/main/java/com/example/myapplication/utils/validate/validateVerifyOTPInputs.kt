package com.example.myapplication.utils.validate

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.domain.model.verify_otp.VerifyOTPRequest

fun validateVerifyOTPInputs(
    verifyOTP: String,
    email: String,
    setVerifyOTPError: (WrongVerify) -> Unit,
    context: Context
): VerifyOTPRequest? {
    var hasError = false

    // Validate VerifyOTP
    when {
        verifyOTP.isBlank() -> {
            setVerifyOTPError(WrongVerify(true, context.getString(R.string.error_empty_otp)))
            hasError = true
        }
        verifyOTP.length < 6 -> {
            setVerifyOTPError(WrongVerify(true, context.getString(R.string.error_short_otp)))
            hasError = true
        }
        else -> setVerifyOTPError(WrongVerify(false, ""))
    }


    return if (hasError) null else VerifyOTPRequest(
        email = email.trim().lowercase(),
        otp = verifyOTP,

    )
}

package com.example.myapplication.domain.model.verify_otp

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOTPResponse(
    val messageEn: String,
    val messageAr: String,
    val isVerifyOTP: Boolean,
    val statusCode: Int
)

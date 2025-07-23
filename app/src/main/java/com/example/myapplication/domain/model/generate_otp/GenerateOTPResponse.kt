package com.example.myapplication.domain.model.generate_otp

import kotlinx.serialization.Serializable

@Serializable
data class GenerateOTPResponse(
    val messageEn: String,
    val messageAr: String,
    val isGenerateOTP: Boolean,
    val statusCode: Int
)
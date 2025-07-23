package com.example.myapplication.domain.model.reset_password

import kotlinx.serialization.Serializable

@Serializable
data class NewPasswordAndOtpResponse(
    val messageEn: String,
    val messageAr: String,
    val isResetPassword: Boolean,
    val statusCode: Int
)

package com.example.myapplication.domain.model.reset_password

import kotlinx.serialization.Serializable

@Serializable
data class NewPasswordAndOtpRequest (
    val email: String,
    val otp: String,
    val newPassword: String
)
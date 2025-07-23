package com.example.myapplication.domain.model.verify_otp

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOTPRequest(
    val email: String,
    val otp: String
)

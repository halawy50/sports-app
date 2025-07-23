package com.example.myapplication.domain.model.generate_otp

import kotlinx.serialization.Serializable

@Serializable
data class GenerateOTPRequest(
    val email:String
)

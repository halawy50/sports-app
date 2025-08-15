package com.example.myapplication.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LogOutResponse(
    val messageAr: String,
    val messageEn: String,
    val isLogOut: Boolean,
    val statusCode: Int
)

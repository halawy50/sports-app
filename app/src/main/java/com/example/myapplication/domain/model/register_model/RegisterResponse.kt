package com.example.myapplication.domain.model.register_model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse (
    val messageAr: String,
    val messageEn: String,
    val isRegister: Boolean,
    val data: DataToken? = null,
    val statusCode: Int
)

@Serializable
data class DataToken(
    val userId: String,
    val accessToken: String,
    val refreshToken : String
)
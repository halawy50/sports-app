package com.example.myapplication.domain.model.challenge_model


import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val isSuccessful: Boolean,
    val messageAr: String,
    val messageEn: String,
    val statusCode: Int
)
package com.example.myapplication.domain.model.token_model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    val accessToken: String,
    val createdAt: String,
    val expireAt: String,
    val isWork: Boolean,
    val messageAr: String,
    val messageEn: String,
    val statusCode: Int
)
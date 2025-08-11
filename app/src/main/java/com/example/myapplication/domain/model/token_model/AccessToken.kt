package com.example.myapplication.domain.model.token_model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("expireAt")
    val expireAt: String,
    @SerialName("isWork")
    val isWork: Boolean,
    @SerialName("messageAr")
    val messageAr: String,
    @SerialName("messageEn")
    val messageEn: String,
    @SerialName("statusCode")
    val statusCode: Int
)
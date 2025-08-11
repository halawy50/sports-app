package com.example.myapplication.domain.model.challenge_model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeResponse1(
    @SerializedName("isPost")
    val isPost: Boolean,
    @SerializedName("messageAr")
    val messageAr: String,
    @SerializedName("messageEn")
    val messageEn: String,
    @SerializedName("statusCode")
    val statusCode: Int
)
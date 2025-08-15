package com.example.myapplication.domain.model.challenge_model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeUserResponse(
    @SerializedName("data")
    val `data`: List<Challenge>,
    @SerializedName("isGetData")
    val isGetData: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)
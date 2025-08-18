package com.example.myapplication.domain.model.challenge_model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeResponsePage(
    @SerializedName("challengesNumberPage")
    val challengesNumberPage: Int,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("results")
    val results: List<ChallengeResult>,
    @SerializedName("totalPages")
    val totalPages: Int
)
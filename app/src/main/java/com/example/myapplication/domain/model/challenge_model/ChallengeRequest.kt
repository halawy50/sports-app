package com.example.myapplication.domain.model.challenge_model


import kotlinx.serialization.Serializable

@Serializable
data class ChallengeRequest(
    val descriptionPost: String,
    val club: String,
    val governorateId: Int,
    val cityId: Int,
    val genderChallengeIndex: Int,
    val team: Int,
    val whatsUpNumber: String
)
package com.example.myapplication.domain.model.challenge_model


import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Governorate
import com.google.gson.annotations.SerializedName

data class ChallengeResult(
    @SerializedName("postId")
    val challengeId: String,
    @SerializedName("userFK")
    val userFK: String,
    @SerializedName("city")
    val city: City,
    @SerializedName("club")
    val club: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("genderChallengeIndex")
    val genderChallengeIndex: Int,
    @SerializedName("genderUserIndex")
    val genderUserIndex: Int,
    @SerializedName("governorate")
    val governorate: Governorate,
    @SerializedName("namePlayer")
    val namePlayer: String,
    @SerializedName("team")
    val team: Int,
    @SerializedName("upload")
    val upload: String,
    @SerializedName("whatsUpNumber")
    val whatsUpNumber: String
)
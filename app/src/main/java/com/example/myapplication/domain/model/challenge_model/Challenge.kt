package com.example.myapplication.domain.model.challenge_model

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.Governorate
import com.google.gson.annotations.SerializedName


data class Challenge(
    @SerializedName("city")
    val city: City,
    @SerializedName("namePlayer")
    val namePlayer:  String = String(),
    @SerializedName("club")
    val club: String = String(),
    @SerializedName("description")
    val description: String = String(),
    @SerializedName("gender")
    val gender: Gender,
    @SerializedName("governorate")
    val governorate: Governorate,
    @SerializedName("postId")
    val challengeID: String,
    @SerializedName("team")
    val team: Int,
    @SerializedName("upload")
    val upload: String = String(),
    @SerializedName("userFK")
    val userFK: String,
    @SerializedName("whatsUpNumber")
    val whatsUpNumber: String,
    @SerializedName("genderUserId")
    val genderUserId: String

)

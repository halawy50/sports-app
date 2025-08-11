package com.example.myapplication.domain.model.post_model

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.Governorate
import com.google.gson.annotations.SerializedName


data class Post(
    @SerializedName("city")
    val city: City,
    @SerializedName("namePlayer")
    val namePlayer: String,
    @SerializedName("photoPlayer")
    val photoPlayer: String,
    @SerializedName("club")
    val club: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("gender")
    val gender: Gender,
    @SerializedName("governorate")
    val governorate: Governorate,
    @SerializedName("postId")
    val postId: String,
    @SerializedName("team")
    val team: Int,
    @SerializedName("upload")
    val upload: String,
    @SerializedName("userFK")
    val userFK: String
)

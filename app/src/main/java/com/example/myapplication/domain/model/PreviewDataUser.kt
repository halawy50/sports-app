package com.example.myapplication.domain.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviewDataUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("name")
    val name: String
)
package com.example.myapplication.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviewDataUser(
    @SerialName("email")
    val email: String,
    @SerialName("gender")
    val gender: Int,
    @SerialName("name")
    val name: String
)
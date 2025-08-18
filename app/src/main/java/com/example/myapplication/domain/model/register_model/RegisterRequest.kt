package com.example.myapplication.domain.model.register_model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("age")
    val age: Int,
    @SerialName("cityId")
    val cityId: Int,
    @SerialName("email")
    val email: String,
    @SerialName("fullName")
    val fullName: String,
    @SerialName("genderIndex")
    val genderIndex: Int,
    @SerialName("governorateId")
    val governorateId: Int,
    @SerialName("password")
    val password: String
)
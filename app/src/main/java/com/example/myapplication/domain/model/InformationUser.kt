package com.example.myapplication.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class InformationUser(
    val fullName: String,
    val email: String,
    val gender: Gender,
    val governorate: Governorate,
    val city: City,
    val age: Int,
)

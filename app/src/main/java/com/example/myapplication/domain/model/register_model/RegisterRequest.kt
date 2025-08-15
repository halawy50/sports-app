package com.example.myapplication.domain.model.register_model

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.Governorate
import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest (
    val fullName: String,
    val gender: Gender,
    val governorate: Governorate,
    val city: City,
    val age: Int,
    val email: String,
    val password: String,
)
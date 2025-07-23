package com.example.myapplication.domain.model.login_model

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Gender
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val age: Int? = null,
    val city: City? = null,
    val gender: Gender? = null,
    val messageAr: String,
    val messageEn: String,
    val isLogin: Boolean,
    val statusCode: Int

)
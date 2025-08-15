package com.example.myapplication.domain.model.login_model

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.Governorate
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val userId: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val messageAr: String,
    val messageEn: String,
    val isLogin: Boolean,
    val statusCode: Int

)
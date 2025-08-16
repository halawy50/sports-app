package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.model.login_model.LoginResponse
import retrofit2.Response

interface LogOutRepository {
    suspend fun logOut(refreshToken: String): Response<ResponseData>
}
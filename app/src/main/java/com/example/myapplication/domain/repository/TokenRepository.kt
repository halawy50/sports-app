package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.token_model.AccessToken
import retrofit2.Response

interface TokenRepository {
    suspend fun generateAccessToken(refreshToken: String): Response<AccessToken>

}
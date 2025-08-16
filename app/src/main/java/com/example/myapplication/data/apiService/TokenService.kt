package com.example.myapplication.data.apiService

import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.model.token_model.AccessToken
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenService {
    @POST("generate_access_token")
    suspend fun generateAccessToken(@Header("Authorization") refreshToken: String): Response<AccessToken>

    @POST("verify_access_token")
    suspend fun verifyAccessToken(@Header("Authorization") refreshToken: String): Response<ResponseData>

}
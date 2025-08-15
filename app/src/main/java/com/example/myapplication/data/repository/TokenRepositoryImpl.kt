package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.TokenService
import com.example.myapplication.domain.model.token_model.AccessToken
import com.example.myapplication.domain.repository.TokenRepository
import retrofit2.Response
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenService: TokenService
): TokenRepository {
    override suspend fun generateAccessToken(refreshToken: String): Response<AccessToken> {
        return tokenService.generateAccessToken(refreshToken = refreshToken)
    }
}
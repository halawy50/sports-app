package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.TokenRepository

class TokenUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(refreshToken: String) = tokenRepository.generateAccessToken(refreshToken = refreshToken)
}
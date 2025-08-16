package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.LogOutRepository

class LogOutUseCase(private val logOutRepository: LogOutRepository) {
    suspend operator fun invoke(refreshToken : String) = logOutRepository.logOut(refreshToken = refreshToken)
}
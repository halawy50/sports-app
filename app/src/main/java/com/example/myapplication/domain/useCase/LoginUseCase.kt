package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.login_model.LoginRequest
import com.example.myapplication.domain.repository.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
){
    suspend operator fun invoke(loginRequest: LoginRequest) = loginRepository.login(loginRequest = loginRequest)
}
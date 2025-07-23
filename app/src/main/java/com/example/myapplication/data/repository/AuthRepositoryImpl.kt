package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.domain.model.register_model.RegisterRequest
import com.example.myapplication.domain.model.register_model.RegisterResponse
import com.example.myapplication.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authService: AuthService): AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return authService.register(registerRequest = registerRequest)
    }
}
package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.domain.model.register_model.RegisterRequest
import com.example.myapplication.domain.model.register_model.RegisterResponse
import com.example.myapplication.domain.repository.RegisterRepository
import retrofit2.Response
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val authService: AuthService): RegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return authService.register(registerRequest = registerRequest)
    }
}
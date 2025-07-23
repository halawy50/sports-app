package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.register_model.RegisterRequest
import com.example.myapplication.domain.model.register_model.RegisterResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse>
}
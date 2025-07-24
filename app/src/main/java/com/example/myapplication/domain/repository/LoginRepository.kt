package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.login_model.LoginRequest
import com.example.myapplication.domain.model.login_model.LoginResponse
import com.example.myapplication.domain.model.register_model.RegisterRequest
import com.example.myapplication.domain.model.register_model.RegisterResponse
import retrofit2.Response

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>
}
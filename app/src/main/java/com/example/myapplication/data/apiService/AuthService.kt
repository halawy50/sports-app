package com.example.myapplication.data.apiService

import com.example.myapplication.domain.model.login_model.LoginRequest
import com.example.myapplication.domain.model.login_model.LoginResponse
import com.example.myapplication.domain.model.register_model.RegisterRequest
import com.example.myapplication.domain.model.register_model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}
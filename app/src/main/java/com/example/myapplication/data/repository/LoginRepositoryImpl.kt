package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.domain.model.login_model.LoginRequest
import com.example.myapplication.domain.model.login_model.LoginResponse
import com.example.myapplication.domain.model.register_model.RegisterRequest
import com.example.myapplication.domain.model.register_model.RegisterResponse
import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.domain.repository.RegisterRepository
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val authService: AuthService): LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return authService.login(loginRequest = loginRequest)
    }
}
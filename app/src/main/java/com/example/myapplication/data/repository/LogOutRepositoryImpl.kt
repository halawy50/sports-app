package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.model.login_model.LoginResponse
import com.example.myapplication.domain.repository.LogOutRepository
import retrofit2.Response
import javax.inject.Inject

class LogOutRepositoryImpl @Inject constructor(private val authService: AuthService) : LogOutRepository {
    override suspend fun logOut(refreshToken: String): Response<ResponseData> {
       return authService.logOut(refreshToken = refreshToken)
    }
}
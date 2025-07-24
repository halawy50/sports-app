package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.domain.model.generate_otp.GenerateOTPRequest
import com.example.myapplication.domain.model.generate_otp.GenerateOTPResponse
import com.example.myapplication.domain.repository.SendOTPRepository
import retrofit2.Response
import javax.inject.Inject

class SendOTPRepositoryImpl @Inject constructor(private val authService: AuthService):SendOTPRepository {
    override suspend fun sendOTP(generateOTPRequest: GenerateOTPRequest): Response<GenerateOTPResponse> {
        return authService.sendOTP(sendOTPRequest = generateOTPRequest)
    }
}
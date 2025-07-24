package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.generate_otp.GenerateOTPRequest
import com.example.myapplication.domain.model.generate_otp.GenerateOTPResponse
import retrofit2.Response

interface SendOTPRepository {
    suspend fun sendOTP(generateOTPRequest: GenerateOTPRequest): Response<GenerateOTPResponse>
}
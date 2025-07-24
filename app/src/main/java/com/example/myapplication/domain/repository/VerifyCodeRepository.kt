package com.example.myapplication.domain.repository

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.domain.model.verify_otp.VerifyOTPRequest
import com.example.myapplication.domain.model.verify_otp.VerifyOTPResponse
import retrofit2.Response
import javax.inject.Inject

interface VerifyCodeRepository {
    suspend fun verifyCode(verifyOTPRequest: VerifyOTPRequest): Response<VerifyOTPResponse>
}
package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.domain.model.verify_otp.VerifyOTPRequest
import com.example.myapplication.domain.model.verify_otp.VerifyOTPResponse
import com.example.myapplication.domain.repository.VerifyCodeRepository
import retrofit2.Response
import javax.inject.Inject

class VerifyCodeRepositoryImpl @Inject constructor(private val authService: AuthService): VerifyCodeRepository {
    override suspend fun verifyCode(verifyOTPRequest: VerifyOTPRequest): Response<VerifyOTPResponse> {
        return authService.verifyCode(verifyOTPRequest = verifyOTPRequest)
    }
}
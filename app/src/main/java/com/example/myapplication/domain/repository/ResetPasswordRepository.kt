package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpRequest
import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpResponse
import retrofit2.Response

interface ResetPasswordRepository {
    suspend fun resetPassword(newPasswordAndOtpRequest: NewPasswordAndOtpRequest): Response<NewPasswordAndOtpResponse>
}
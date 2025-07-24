package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpRequest
import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpResponse
import com.example.myapplication.domain.repository.ResetPasswordRepository
import retrofit2.Response
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(
    private val authService: AuthService
):ResetPasswordRepository {
    override suspend fun resetPassword(newPasswordAndOtpRequest: NewPasswordAndOtpRequest): Response<NewPasswordAndOtpResponse> {
        return authService.resetPassword(newPasswordAndOtpRequest = newPasswordAndOtpRequest)
    }
}
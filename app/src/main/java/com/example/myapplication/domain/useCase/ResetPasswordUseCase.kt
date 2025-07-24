package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpRequest
import com.example.myapplication.domain.repository.ResetPasswordRepository

class ResetPasswordUseCase(private val resetPasswordRepository: ResetPasswordRepository) {
    suspend operator fun invoke(newPasswordAndOtpRequest: NewPasswordAndOtpRequest)
        = resetPasswordRepository.resetPassword(newPasswordAndOtpRequest = newPasswordAndOtpRequest)
}
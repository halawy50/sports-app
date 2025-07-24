package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.generate_otp.GenerateOTPRequest
import com.example.myapplication.domain.model.verify_otp.VerifyOTPRequest
import com.example.myapplication.domain.repository.SendOTPRepository
import com.example.myapplication.domain.repository.VerifyCodeRepository

class VerifyCodeUseCase(
    private val verifyCodeRepository: VerifyCodeRepository
) {
    suspend operator fun invoke(verifyOTPRequest: VerifyOTPRequest) =
        verifyCodeRepository.verifyCode(verifyOTPRequest = verifyOTPRequest)
}
package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.generate_otp.GenerateOTPRequest
import com.example.myapplication.domain.repository.SendOTPRepository

class SendOTPUseCase(
    private val sendOTPRepository: SendOTPRepository
) {
    suspend operator fun invoke(generateOTPRequest: GenerateOTPRequest) =
        sendOTPRepository.sendOTP(generateOTPRequest = generateOTPRequest)
}
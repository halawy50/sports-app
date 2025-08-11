package com.example.myapplication.utils

import com.example.myapplication.domain.model.generate_otp.GenerateOTPResponse
import com.example.myapplication.domain.model.login_model.LoginResponse

sealed class StateSendOTP {
    object Idle : StateSendOTP()
    object Loading : StateSendOTP()
    data class Success(val data: GenerateOTPResponse) : StateSendOTP()
    data class Failure(val data: GenerateOTPResponse) : StateSendOTP()
}

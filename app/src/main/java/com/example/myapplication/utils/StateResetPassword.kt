package com.example.myapplication.utils

import com.example.myapplication.domain.model.generate_otp.GenerateOTPResponse
import com.example.myapplication.domain.model.login_model.LoginResponse
import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpResponse
import com.example.myapplication.domain.model.verify_otp.VerifyOTPResponse

sealed class StateResetPassword {
    object Idle : StateResetPassword()
    object Loading : StateResetPassword()
    data class Success(val data: NewPasswordAndOtpResponse) : StateResetPassword()
    data class Failure(val data: NewPasswordAndOtpResponse) : StateResetPassword()
}

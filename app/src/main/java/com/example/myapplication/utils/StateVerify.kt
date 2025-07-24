package com.example.myapplication.utils

import com.example.myapplication.domain.model.generate_otp.GenerateOTPResponse
import com.example.myapplication.domain.model.login_model.LoginResponse
import com.example.myapplication.domain.model.verify_otp.VerifyOTPResponse

sealed class StateVerify {
    object Idle : StateVerify()
    object Loading : StateVerify()
    data class Success(val data: VerifyOTPResponse) : StateVerify()
    data class Failure(val data: VerifyOTPResponse) : StateVerify()
}

package com.example.myapplication.utils

import com.example.myapplication.domain.model.GovernorateListWrapper
import com.example.myapplication.domain.model.generate_otp.GenerateOTPResponse
import com.example.myapplication.domain.model.login_model.LoginResponse

sealed class StateGovernorate {
    object Idle : StateGovernorate()
    object Loading : StateGovernorate()
    data class Success(val data: GovernorateListWrapper) : StateGovernorate()
    data class Failure(val data: GenerateOTPResponse) : StateGovernorate()
}

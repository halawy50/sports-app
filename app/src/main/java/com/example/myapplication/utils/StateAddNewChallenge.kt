package com.example.myapplication.utils

import com.example.myapplication.domain.model.login_model.LoginResponse

sealed class StateLogin {
    object Idle : StateLogin()
    object Loading : StateLogin()
    data class Success(val data: LoginResponse) : StateLogin()
    data class Failure(val data: LoginResponse) : StateLogin()
}

package com.example.myapplication.utils

import com.example.myapplication.domain.model.register_model.RegisterResponse

sealed class StateRegister {
    object Idle : StateRegister()
    object Loading : StateRegister()
    data class Success(val data: RegisterResponse) : StateRegister()
    data class Failure(val data: RegisterResponse) : StateRegister()
}

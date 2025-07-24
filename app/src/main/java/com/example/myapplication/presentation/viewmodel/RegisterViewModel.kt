package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.register_model.RegisterRequest
import com.example.myapplication.domain.model.register_model.RegisterResponse
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.domain.useCase.RegisterUseCase
import com.example.myapplication.utils.StateRegister
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase): ViewModel() {
    private val _state = MutableStateFlow<StateRegister>(StateRegister.Idle)
    val state: StateFlow<StateRegister> = _state

    fun register(registerRequest: RegisterRequest){
        Log.d("Register", "Sending request: $registerRequest")

        viewModelScope.launch {

            try {
                _state.value = StateRegister.Loading

                val result = registerUseCase(registerRequest)

                if (result.isSuccessful && result.body() != null) {
                    _state.value = StateRegister.Success(result.body()!!)
                } else {
                    val gson = Gson()
                    val errorBody = result.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBody, RegisterResponse::class.java)

                    _state.value = StateRegister.Failure(data = errorResponse)
                }
            } catch (e: Exception) {
                _state.value = StateRegister.Failure(
                    data = RegisterResponse(
                        messageAr = "حدث خطأ غير متوقع",
                        messageEn = "Unexpected error occurred",
                        isRegister = false,
                        statusCode = 500
                    )
                )
            }


        }
    }

    fun resetState() {
        _state.value = StateRegister.Idle
    }

}
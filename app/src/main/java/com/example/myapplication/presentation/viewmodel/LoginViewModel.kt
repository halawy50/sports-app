package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.login_model.LoginRequest
import com.example.myapplication.domain.model.login_model.LoginResponse
import com.example.myapplication.domain.useCase.LoginUseCase
import com.example.myapplication.utils.StateLogin
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {
    private val _state = MutableStateFlow<StateLogin>(StateLogin.Idle)
    val state: StateFlow<StateLogin> = _state

    fun login(loginRequest: LoginRequest){

        viewModelScope.launch {

            try {
                _state.value = StateLogin.Loading

                val result = loginUseCase(loginRequest)

                if (result.isSuccessful && result.body() != null) {
                    _state.value = StateLogin.Success(result.body()!!)
                } else {
                    val gson = Gson()
                    val errorBody = result.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBody, LoginResponse::class.java)

                    _state.value = StateLogin.Failure(data = errorResponse)
                }
            } catch (e: Exception) {
                Log.d("Login", "Sending request: $e")

                _state.value = StateLogin.Failure(
                    data = LoginResponse(
                        messageAr = "حدث خطأ غير متوقع",
                        messageEn = "Unexpected error occurred , ${e}",
                        isLogin = false,
                        statusCode = 500
                    )
                )
            }


        }
    }

    fun resetState() {
        _state.value = StateLogin.Idle
    }

}
package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.register_model.RegisterResponse
import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpRequest
import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpResponse
import com.example.myapplication.domain.useCase.ResetPasswordUseCase
import com.example.myapplication.utils.StateRegister
import com.example.myapplication.utils.StateResetPassword
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val resetPasswordUseCase: ResetPasswordUseCase): ViewModel() {
    private val _state = MutableStateFlow<StateResetPassword>(StateResetPassword.Idle)
    val state: StateFlow<StateResetPassword> = _state

    fun resetPassword(newPasswordAndOtpRequest: NewPasswordAndOtpRequest){
        viewModelScope.launch {
            try {
                _state.value = StateResetPassword.Loading

                val result = resetPasswordUseCase(newPasswordAndOtpRequest)

                if(result.isSuccessful && result.body() != null){
                    _state.value = StateResetPassword.Success(data = result.body()!!)
                }else{
                    val gson = Gson()
                    val errorBody = result.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBody, NewPasswordAndOtpResponse::class.java)
                    _state.value = StateResetPassword.Failure(data = errorResponse)
                }
            }catch (e: Exception){
                _state.value = StateResetPassword.Failure(
                    data = NewPasswordAndOtpResponse(
                        messageAr = "حدث خطأ غير متوقع",
                        messageEn = "Unexpected error occurred",
                        isResetPassword = false,
                        statusCode = 500
                    )
                )
            }
        }
    }

    fun resetState() {
        _state.value = StateResetPassword.Idle
    }
}
package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.generate_otp.GenerateOTPRequest
import com.example.myapplication.domain.model.generate_otp.GenerateOTPResponse
import com.example.myapplication.domain.model.register_model.RegisterResponse
import com.example.myapplication.domain.useCase.SendOTPUseCase
import com.example.myapplication.utils.StateRegister
import com.example.myapplication.utils.StateSendOTP
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@dagger.hilt.android.lifecycle.HiltViewModel
class SendOTPViewModel @Inject constructor(private val sendOTPUseCase: SendOTPUseCase): ViewModel(){

    private val _state = MutableStateFlow<StateSendOTP>(StateSendOTP.Idle)
    val state : StateFlow<StateSendOTP> = _state

    fun sendOTP(generateOTPRequest: GenerateOTPRequest) {
        viewModelScope.launch {
            try {
                _state.value = StateSendOTP.Loading

                val result = sendOTPUseCase(generateOTPRequest = generateOTPRequest)
                if(result.isSuccessful && result.body() != null){
                    _state.value = StateSendOTP.Success(result.body()!!)

                }else{
                    val gson = Gson()
                    val errorBody = result.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBody , GenerateOTPResponse::class.java)

                    _state.value = StateSendOTP.Failure(data = errorResponse)
                }
            }catch (e: Exception){
                _state.value = StateSendOTP.Failure(
                    data = GenerateOTPResponse(
                        messageAr = "حدث خطأ غير متوقع",
                        messageEn = "Unexpected error occurred",
                        isGenerateOTP = false,
                        statusCode = 500
                    )
                )
            }
        }
    }

    fun resetState() {
        _state.value = StateSendOTP.Idle
    }
}
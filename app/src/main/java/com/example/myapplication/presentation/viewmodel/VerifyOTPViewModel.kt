package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.generate_otp.GenerateOTPResponse
import com.example.myapplication.domain.model.verify_otp.VerifyOTPRequest
import com.example.myapplication.domain.model.verify_otp.VerifyOTPResponse
import com.example.myapplication.domain.useCase.VerifyCodeUseCase
import com.example.myapplication.utils.StateSendOTP
import com.example.myapplication.utils.StateVerify
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOTPViewModel @Inject constructor(
    private val verifyCodeUseCase: VerifyCodeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<StateVerify>(StateVerify.Idle)
    val state : StateFlow<StateVerify> = _state

    private val startingValue = 60
    private val _counterOTP = MutableStateFlow(startingValue)
    val counterOTP: StateFlow<Int> = _counterOTP

    init {
        startCounter()
    }

    fun verifyOTP(verifyCodeRequest: VerifyOTPRequest){
        viewModelScope.launch {
            try {
                _state.value = StateVerify.Loading
                val result = verifyCodeUseCase(verifyOTPRequest = verifyCodeRequest)
                if(result.isSuccessful && result.body() != null){
                    _state.value = StateVerify.Success(result.body()!!)
                }else{
                    val gson = Gson()

                    val errorBody = result.errorBody()?.string()

                    val errorResponse = gson.fromJson(errorBody , VerifyOTPResponse::class.java)

                    _state.value = StateVerify.Failure(data = errorResponse)
                }

            }catch (e: Exception){
                _state.value = StateVerify.Failure(
                    data = VerifyOTPResponse(
                        messageAr = "حدث خطأ غير متوقع",
                        messageEn = "Unexpected error occurred",
                        isVerifyOTP = false,
                        statusCode = 500
                    )
                )
            }
        }
    }


    private fun startCounter() {
        viewModelScope.launch {

            var current = startingValue
            _counterOTP.value = current
            while (current > 0) {
                delay(1000L)
                current--
                _counterOTP.value = current
            }
        }
    }

    fun onResendCode() {
        startCounter()
    }

    fun resetState() {
        _state.value = StateVerify.Idle
    }
}

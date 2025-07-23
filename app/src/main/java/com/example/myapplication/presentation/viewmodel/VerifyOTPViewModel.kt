package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOTPViewModel @Inject constructor() : ViewModel() {

    private val startingValue = 60

    private val _counterOTP = MutableStateFlow(startingValue)
    val counterOTP: StateFlow<Int> = _counterOTP

    init {
        startCounter()
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
}

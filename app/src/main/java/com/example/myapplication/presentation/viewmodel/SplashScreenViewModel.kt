package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.useCase.TokenUseCase
import com.example.myapplication.utils.GlobalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val tokenUseCase: TokenUseCase,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _stateLogin = MutableStateFlow<GlobalState>(GlobalState.IDLE)
    val stateLogin : StateFlow<GlobalState> = _stateLogin

    init {
        logInUseRefreshToken()
    }

    fun logInUseRefreshToken() {
        viewModelScope.launch {
            try {
                _stateLogin.value = GlobalState.LOADING
                Log.e("SplashScreenViewModel", "LOADING", )

                val refreshToken = tokenManager.getRefreshToken().orEmpty()
                if (refreshToken.isBlank()) {
                    Log.e("SplashScreenViewModel", "ERROR", )
                    _stateLogin.value = GlobalState.ERROR
                    return@launch
                }

                val result = tokenUseCase(refreshToken)

                if (result.isSuccessful && result.body() != null) {
                    tokenManager.saveAccessToken(result.body()!!.accessToken)
                    Log.e("SplashScreenViewModel", "SUCCESS", )

                    _stateLogin.value = GlobalState.SUCCESS
                } else {
                    Log.e("SplashScreenViewModel", "ERROR", )
                    _stateLogin.value = GlobalState.ERROR
                }


            } catch (e: Exception) {
                Log.e("SplashScreenViewModel", "Error refreshing token", e)
                _stateLogin.value = GlobalState.ERROR
            }
        }
    }


}
package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.useCase.LogOutUseCase
import com.example.myapplication.utils.GlobalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogOutViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _stateLogOut = MutableStateFlow<GlobalState>(GlobalState.IDLE)
    val stateLogOut: StateFlow<GlobalState> = _stateLogOut

    fun logOut() {
        Log.d("Logut" , "1")
        viewModelScope.launch {
            Log.d("Logut" , "2")

            _stateLogOut.value = GlobalState.LOADING
            Log.d("Logut" , "3")

            val refreshToken = tokenManager.getRefreshToken().orEmpty()
            Log.d("Logut" , "4")

            if (refreshToken.isEmpty()) {
                Log.d("Logut" , "5")

                clearTokens()
                Log.d("Logut" , "6")

                _stateLogOut.value = GlobalState.SUCCESS
                Log.d("Logut" , "7")

            }

            try {
                Log.d("Logut" , "8")

                logOutUseCase(refreshToken)
                Log.d("Logut" , "9")

                clearTokens()
                Log.d("Logut" , "10")

                _stateLogOut.value = GlobalState.SUCCESS
                Log.d("Logut" , "11")

            } catch (e: Exception) {
                Log.d("Logut" , "12")

                clearTokens()
                Log.d("Logut" , "13")

                _stateLogOut.value = GlobalState.ERROR
                Log.d("Logut" , "14")

            }
        }
    }

    private fun clearTokens() {
        tokenManager.saveLoginToken(userId = "", refreshToken = "", accessToken = "")
    }

    fun resetStateLogOut(){
        _stateLogOut.value = GlobalState.IDLE
    }
}

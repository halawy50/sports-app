package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.model.InformationUser
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.useCase.InformationUserUseCase
import com.example.myapplication.utils.GlobalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationUserViewModel @Inject constructor(
    private val informationUserUseCase: InformationUserUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _stateFetchData = MutableStateFlow<GlobalState>(GlobalState.IDLE)
    val stateFetchData: StateFlow<GlobalState> = _stateFetchData

    private val _informationUser = MutableStateFlow<InformationUser?>(null)
    val informationUser: StateFlow<InformationUser?> = _informationUser

    init {
        getInformationUser()
    }
    fun getInformationUser(){
        viewModelScope.launch {
            _stateFetchData.value = GlobalState.LOADING
            try {
                val informationUserResult = informationUserUseCase(tokenManager.getUserId().toString())

                if (informationUserResult.isSuccessful && informationUserResult.body() != null){
                    Log.d("InformationUser", "${informationUserResult.body()}")

                    _stateFetchData.value = GlobalState.SUCCESS
                    _informationUser.value = informationUserResult.body()
                }else if (informationUserResult.isSuccessful() && informationUserResult.body() == null){
                    Log.d("InformationUser", "EMPTY")
                    _stateFetchData.value = GlobalState.EMPTY
                }else{
                    Log.d("InformationUser", "Error")
                    _stateFetchData.value = GlobalState.ERROR
                }

            }catch (e: Exception){
                Log.d("InformationUser", "${ResponseData(
                    isSuccessful = false,
                    messageAr = "حدث خطأ غير متوقع",
                    messageEn = "Unexpected error occurred: ${e.message}",
                    statusCode = 500
                )}")
                _stateFetchData.value = GlobalState.ERROR

            }
        }
    }
}
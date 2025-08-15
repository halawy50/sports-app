package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.useCase.TokenUseCase
import com.example.myapplication.domain.useCase.UpdateChallengeUseCase
import com.example.myapplication.utils.GlobalState
import com.example.myapplication.utils.StateUpdateChallenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateChallengeViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val tokenUseCase: TokenUseCase,
    private val updateChallengeUseCase: UpdateChallengeUseCase
): ViewModel() {

    private val _challenge = MutableStateFlow<Challenge?>(null)
    val challenge: StateFlow<Challenge?> = _challenge

    private val _stateGetChallenge = MutableStateFlow<GlobalState>(GlobalState.IDLE)
    val stateGetChallenge: StateFlow<GlobalState> = _stateGetChallenge

    private val _stateUpdateChallenge = MutableStateFlow<StateUpdateChallenge>(StateUpdateChallenge.Idle)
    val stateUpdateChallenge: StateFlow<StateUpdateChallenge> = _stateUpdateChallenge



    fun updateChallenge(challengeID : String, challengeRequest: ChallengeRequest){
        viewModelScope.launch {
            try {
                val accessToken = tokenManager.getAccessToken().orEmpty()
                Log.d("ProcessAddNewChallenge", "Data: $challengeRequest\nToken: $accessToken")

                _stateUpdateChallenge.value = StateUpdateChallenge.Loading

                val result = updateChallengeUseCase(
                    accessToken = accessToken,
                    challengeRequest = challengeRequest,
                    postID = challengeID
                )

                Log.d("ProcessUpdateNewChallenge", "Response Code: ${result.code()}")
                Log.d("ProcessUpdateNewChallenge", "Response Body: ${result.body()}")

                if (result.isSuccessful && result.body() != null) {
                    _stateUpdateChallenge.value = StateUpdateChallenge.Success(data = result.body()!!)
                    Log.d("ProcessUpdateNewChallenge", "Success")
                } else if (result.code() == 401) {
                    val refreshToken = tokenManager.getRefreshToken().orEmpty()

                    if (refreshToken.isBlank()) {
                        _stateUpdateChallenge.value = StateUpdateChallenge.UnAuthorization
                        Log.d("ProcessUpdateNewChallenge", "401 - No Refresh Token")
                        return@launch
                    }

                    val refreshResult = tokenUseCase(refreshToken = refreshToken)
                    val newToken = refreshResult.body()?.accessToken

                    Log.d("ProcessUpdateNewChallenge", "Refresh Token Result: ${refreshResult.body()}")

                    if (refreshResult.isSuccessful && !newToken.isNullOrBlank()) {
                        tokenManager.saveAccessToken(accessToken = newToken)
                        updateChallenge(challengeID = challengeID, challengeRequest = challengeRequest)
                    } else {
                        _stateUpdateChallenge.value = StateUpdateChallenge.UnAuthorization
                        Log.d("ProcessUpdateNewChallenge", "401 - Refresh Failed")
                    }

                } else {
                    val errorBody = result.body()
                    if (errorBody != null) {
                        _stateUpdateChallenge.value = StateUpdateChallenge.Failure(data = errorBody)
                    } else {
                        _stateUpdateChallenge.value = StateUpdateChallenge.Failure(
                            data = ResponseData(
                                isSuccessful = false,
                                messageAr = "فشل غير معروف",
                                messageEn = "Unknown error occurred",
                                statusCode = result.code()
                            )
                        )
                    }

                    Log.d("ProcessUpdateNewChallenge", "Failure with code: ${result.code()}")
                }

            } catch (e: Exception) {
                _stateUpdateChallenge.value = StateUpdateChallenge.Failure(
                    data = ResponseData(
                        isSuccessful = false,
                        messageAr = "حدث خطأ غير متوقع بالرجاء التأكد من الاتصال بالانترنت",
                        messageEn = "Unexpected error occurred, please check your internet",
                        statusCode = 500
                    )
                )
                Log.e("ProcessUpdateNewChallenge", "Exception: $e", e)
            }
        }
    }

    fun getSingleChallenge(challengeID: String){

        viewModelScope.launch {
            try {
                _stateGetChallenge.value = GlobalState.LOADING
                val result = updateChallengeUseCase.getSingleChallenge(challengeID = challengeID)

                if (result.isSuccessful && result.code() == 200 && result.body() != null){
                    _stateGetChallenge.value = GlobalState.READY
                    _challenge.value = result.body()
                }else{
                    _stateGetChallenge.value = GlobalState.EMPTY
                }

            }catch (e: Exception){
                _stateGetChallenge.value = GlobalState.Error
            }

        }
    }

    fun resetState(){
        _stateUpdateChallenge.value = StateUpdateChallenge.Idle
    }
}
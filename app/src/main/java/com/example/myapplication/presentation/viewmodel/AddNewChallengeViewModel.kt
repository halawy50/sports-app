package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.useCase.AddNewChallengeUseCase
import com.example.myapplication.domain.useCase.TokenUseCase
import com.example.myapplication.utils.StateAddNewChallenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewChallengeViewModel @Inject constructor(
    private val addNewChallengeUseCase: AddNewChallengeUseCase,
    private val tokenManager: TokenManager,
    private val tokenUseCase: TokenUseCase
) : ViewModel() {

    private val _stateAddNewChallenge = MutableStateFlow<StateAddNewChallenge>(StateAddNewChallenge.Idle)
    val stateAddNewChallenge: StateFlow<StateAddNewChallenge> = _stateAddNewChallenge

    fun addNewChallenge(challengeRequest: ChallengeRequest) {
        viewModelScope.launch {
            try {
                val accessToken = tokenManager.getAccessToken().orEmpty()
                Log.d("ProcessAddNewChallenge", "Data: $challengeRequest\nToken: $accessToken")

                _stateAddNewChallenge.value = StateAddNewChallenge.Loading

                val result = addNewChallengeUseCase(
                    token = accessToken,
                    challengeRequest = challengeRequest
                )

                Log.d("ProcessAddNewChallenge", "Response Code: ${result.code()}")
                Log.d("ProcessAddNewChallenge", "Response Body: ${result.body()}")

                if (result.isSuccessful && result.body() != null) {
                    _stateAddNewChallenge.value = StateAddNewChallenge.Success(data = result.body()!!)
                    Log.d("ProcessAddNewChallenge", "Success")

                } else if (result.code() == 401) {
                    val refreshToken = tokenManager.getRefreshToken().orEmpty()

                    if (refreshToken.isBlank()) {
                        _stateAddNewChallenge.value = StateAddNewChallenge.UnAuthorization
                        Log.d("ProcessAddNewChallenge", "401 - No Refresh Token")
                        return@launch
                    }

                    val refreshResult = tokenUseCase(refreshToken = refreshToken)
                    val newToken = refreshResult.body()?.accessToken

                    Log.d("ProcessAddNewChallenge", "Refresh Token Result: ${refreshResult.body()}")

                    if (refreshResult.isSuccessful && !newToken.isNullOrBlank()) {
                        tokenManager.saveAccessToken(accessToken = newToken)
                        addNewChallenge(challengeRequest = challengeRequest)
                    } else {
                        _stateAddNewChallenge.value = StateAddNewChallenge.UnAuthorization
                        Log.d("ProcessAddNewChallenge", "401 - Refresh Failed")
                    }

                } else {
                    val errorBody = result.body()
                    if (errorBody != null) {
                        _stateAddNewChallenge.value = StateAddNewChallenge.Failure(data = errorBody)
                    } else {
                        _stateAddNewChallenge.value = StateAddNewChallenge.Failure(
                            data = ResponseData(
                                isSuccessful = false,
                                messageAr = "فشل غير معروف",
                                messageEn = "Unknown error occurred",
                                statusCode = result.code()
                            )
                        )
                    }

                    Log.d("ProcessAddNewChallenge", "Failure with code: ${result.code()}")
                }

            } catch (e: Exception) {
                _stateAddNewChallenge.value = StateAddNewChallenge.Failure(
                    data = ResponseData(
                        isSuccessful = false,
                        messageAr = "حدث خطأ غير متوقع",
                        messageEn = "Unexpected error occurred: ${e.message}",
                        statusCode = 500
                    )
                )
                Log.e("ProcessAddNewChallenge", "Exception: $e", e)
            }
        }
    }

    fun resetState(){
        _stateAddNewChallenge.value = StateAddNewChallenge.Idle
    }
}

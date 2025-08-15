package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.useCase.DeleteChallengeUseCase
import com.example.myapplication.domain.useCase.TokenUseCase
import com.example.myapplication.utils.RemoveItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteChallengeViewModel @Inject constructor(
    private val deleteChallengeUseCase: DeleteChallengeUseCase,
    private val tokenUseCase: TokenUseCase,
    private val tokenManager: TokenManager,
): ViewModel() {

    private val _removeChallengeState = MutableStateFlow<RemoveItemState>(RemoveItemState.OFF_REMOVE)
    val removeChallengeState: StateFlow<RemoveItemState> = _removeChallengeState

    private val _selectChallengeID = MutableStateFlow<String>("")
    val selectChallengeID: StateFlow<String> = _selectChallengeID


    fun setStateRemove(stateRemove: RemoveItemState, selectChallengeId: String = ""){
       _removeChallengeState.value = stateRemove
       _selectChallengeID.value = selectChallengeId
    }


    fun deleteChallenge(challengeID : String){

        viewModelScope.launch {
            _removeChallengeState.value = RemoveItemState.REMOVING
            try {
                val result = deleteChallengeUseCase(
                    accessToken = tokenManager.getAccessToken().toString(),
                    challengeId = challengeID
                )

                if (result.isSuccessful && result.body() != null){
                    _removeChallengeState.value = RemoveItemState.REMOVED

                } else if (result.code() == 401) {
                    val refreshToken = tokenManager.getRefreshToken().orEmpty()

                    if (refreshToken.isBlank()) {
                        _removeChallengeState.value = RemoveItemState.UNAuthorization
                        Log.d("ProcessAddNewChallenge", "401 - No Refresh Token")
                        return@launch
                    }

                    val refreshResult = tokenUseCase(refreshToken = refreshToken)
                    val newToken = refreshResult.body()?.accessToken

                    Log.d("ProcessAddNewChallenge", "Refresh Token Result: ${refreshResult.body()}")

                    if (refreshResult.isSuccessful && !newToken.isNullOrBlank()) {
                        tokenManager.saveAccessToken(accessToken = newToken)
                        deleteChallenge(challengeID = challengeID)
                    } else {
                        _removeChallengeState.value = RemoveItemState.UNAuthorization
                        Log.d("ProcessAddNewChallenge", "401 - Refresh Failed")
                    }

                } else {
                    _removeChallengeState.value = RemoveItemState.WRONG_WHEN_REMOVE
                    Log.d("ProcessAddNewChallenge", "Failure with code: ${result.code()}")
                }

            } catch (e: Exception) {
                _removeChallengeState.value = RemoveItemState.WRONG_WHEN_REMOVE
                Log.e("ProcessAddNewChallenge", "Exception: $e", e)
            }
        }

    }
}
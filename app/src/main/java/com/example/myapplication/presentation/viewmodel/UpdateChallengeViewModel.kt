package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.useCase.TokenUseCase
import com.example.myapplication.domain.useCase.UpdateChallengeUseCase
import com.example.myapplication.utils.FetchDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val tokenUseCase: TokenUseCase,
    private val updateChallengeUseCase: UpdateChallengeUseCase
): ViewModel() {

    private val _challenge = MutableStateFlow<Challenge?>(null)
    val challenge: StateFlow<Challenge?> = _challenge

    private val _stateGetChallenge = MutableStateFlow<FetchDataState>(FetchDataState.IDLE)
    val stateGetChallenge: StateFlow<FetchDataState> = _stateGetChallenge


    fun getSingleChallenge(challengeID: String){

        viewModelScope.launch {
            _stateGetChallenge.value = FetchDataState.LOADING
            try {

                val result = updateChallengeUseCase.getSingleChallenge(challengeID = challengeID)

                if (result.isSuccessful && result.code() == 200)

            }catch (e: Exception){

            }
        }
    }
}
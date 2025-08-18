package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.model.challenge_model.ChallengeDataUpdate
import com.example.myapplication.domain.model.challenge_model.ChallengeResult
import com.example.myapplication.domain.useCase.ChallengesUserUseCase
import com.example.myapplication.domain.useCase.CityAndGovernorateUseCase
import com.example.myapplication.utils.GlobalState
import com.example.myapplication.utils.UpdateChallenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengesUserViewModel @Inject constructor(
    private val challengesUserUseCase: ChallengesUserUseCase,
    private val tokenManager: TokenManager,
    private val cityAndGovernorateUseCase: CityAndGovernorateUseCase
): UpdateChallenge, ViewModel() {

    private val _challenges = MutableStateFlow<List<ChallengeResult>>(emptyList())
    val challenges: StateFlow<List<ChallengeResult>> = _challenges

    private val _totalPage = MutableStateFlow(1)

    private val _counterPage = MutableStateFlow(1)

    private val _initialState = MutableStateFlow(false)
    val initialState: StateFlow<Boolean> = _initialState

    private val _stateGetChallenges = MutableStateFlow(GlobalState.IDLE)
    val stateGetChallenges: StateFlow<GlobalState> = _stateGetChallenges

    private val isGetFirstData = MutableStateFlow(true)

    // Job for cancellation
    private var challengesJob: Job? = null

    init {
        restartCounterPage()
    }

    fun getAllChallengesUser() {
        val userId = tokenManager.getUserId()

        if (userId == null) {
            Log.e(TAG, "User ID is null, cannot load challenges")
            _stateGetChallenges.value = GlobalState.ERROR
            return
        }

        challengesJob?.cancel()
        challengesJob = viewModelScope.launch {
            try {
                Log.d(TAG, "Loading user challenges - Page: ${_counterPage.value}, UserID: $userId")

                if (_counterPage.value > _totalPage.value) {
                    Log.d(TAG, "All pages loaded")
                    _stateGetChallenges.value = GlobalState.SUCCESS
                    return@launch
                }

                if (_stateGetChallenges.value != GlobalState.LOADING) {
                    _stateGetChallenges.value = GlobalState.LOADING
                }

                val result = challengesUserUseCase(
                    userId = userId.toString(),
                    page = _counterPage.value
                )

                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!

                    if (body.results.isNotEmpty()) {
                        Log.d(TAG, "Success - Loaded ${body.results.size} user challenges")

                        // Set total pages only on first load
                        if (isGetFirstData.value) {
                            isGetFirstData.value = false
                            _totalPage.value = body.totalPages
                            Log.d(TAG, "Total pages set to: ${body.totalPages}")
                        }

                        // Add new challenges to existing list
                        _challenges.value = _challenges.value + body.results
                        _counterPage.value = _counterPage.value + 1
                        _stateGetChallenges.value = GlobalState.SUCCESS
                    } else {
                        Log.d(TAG, "No more user challenges available")
                        _stateGetChallenges.value = if (_challenges.value.isEmpty()) {
                            GlobalState.EMPTY
                        } else {
                            GlobalState.SUCCESS
                        }
                    }
                } else {
                    Log.w(TAG, "Request failed or empty response. Status: ${result.code()}")
                    _stateGetChallenges.value = if (_challenges.value.isEmpty()) {
                        GlobalState.EMPTY
                    } else {
                        GlobalState.SUCCESS
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error loading user challenges", e)
                _stateGetChallenges.value = GlobalState.ERROR
            }
        }
    }

    fun restartCounterPage() {
        challengesJob?.cancel()
        challengesJob = viewModelScope.launch {
            Log.d(TAG, "Restarting user challenges loading")

            _stateGetChallenges.value = GlobalState.LOADING
            _challenges.value = emptyList()
            _counterPage.value = 1
            _totalPage.value = 1
            isGetFirstData.value = true

            getAllChallengesUser()
            _initialState.value = true
        }
    }

    fun removeChallenge(challengeID: String) {
        val initialSize = _challenges.value.size
        _challenges.value = _challenges.value.filterNot { it.challengeId == challengeID }

        if (_challenges.value.size < initialSize) {
            Log.d(TAG, "User challenge $challengeID removed")
            if (_challenges.value.isEmpty()) {
                _stateGetChallenges.value = GlobalState.EMPTY
            }
        } else {
            Log.w(TAG, "Challenge $challengeID not found for removal")
        }
    }

    override fun updateChallenge(challengeID: String, challengeDataUpdate: ChallengeDataUpdate): Boolean {
        var updated = false

        _challenges.update { challenges ->
            challenges.map { item ->
                if (item.challengeId == challengeID) {
                    updated = true
                    Log.d(TAG, "Updating challenge $challengeID")
                    item.copy(
                        description = challengeDataUpdate.description,
                        club = challengeDataUpdate.club,
                        whatsUpNumber = challengeDataUpdate.whatsUpNumber,
                        genderChallengeIndex = challengeDataUpdate.gender.index,
                        team = challengeDataUpdate.team,
                        governorate = challengeDataUpdate.governorate,
                        city = challengeDataUpdate.city
                    )
                } else {
                    item
                }
            }
        }

        if (updated) {
            Log.d(TAG, "Challenge $challengeID updated successfully")
        } else {
            Log.w(TAG, "Challenge $challengeID not found for update")
        }

        return updated
    }

    override fun onCleared() {
        super.onCleared()
        challengesJob?.cancel()
        Log.d(TAG, "ViewModel cleared")
    }

    companion object {
        private const val TAG = "ChallengesUser"
    }
}
package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.domain.model.challenge_model.ChallengeDataUpdate
import com.example.myapplication.domain.model.challenge_model.ChallengeResult
import com.example.myapplication.domain.useCase.HomeChallengesUseCase
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
class HomeChallengesViewModel @Inject constructor(
    private val homeChallengesUseCase: HomeChallengesUseCase,
): UpdateChallenge, ViewModel() {

    // Regular challenges state
    private val _challenges = MutableStateFlow<List<ChallengeResult>>(emptyList())
    val challenges: StateFlow<List<ChallengeResult>> = _challenges

    private val _totalPage = MutableStateFlow(1)
    val totalPage: StateFlow<Int> = _totalPage

    private val _counterPage = MutableStateFlow(1)
    val counterPage: StateFlow<Int> = _counterPage

    private val _initialState = MutableStateFlow(false)
    val initialState: StateFlow<Boolean> = _initialState

    private val _stateGetChallenges = MutableStateFlow(GlobalState.IDLE)
    val stateGetChallenges: StateFlow<GlobalState> = _stateGetChallenges

    // Filter state
    private val _stateFilter = MutableStateFlow(GlobalState.IDLE)
    val stateFilter: StateFlow<GlobalState> = _stateFilter

    private val _totalPageFilter = MutableStateFlow(1)
    val totalPageFilter: StateFlow<Int> = _totalPageFilter

    private val _challengesFilter = MutableStateFlow<List<ChallengeResult>>(emptyList())
    val challengesFilter: StateFlow<List<ChallengeResult>> = _challengesFilter

    private val _currentPageFilter = MutableStateFlow(1)
    val currentPageFilter: StateFlow<Int> = _currentPageFilter

    private val _isFilter = MutableStateFlow(false)
    val isFilter: StateFlow<Boolean> = _isFilter

    private val _initialFilterRequest = MutableStateFlow<FilterRequest?>(null)
    val initialFilterRequest: StateFlow<FilterRequest?> = _initialFilterRequest

    // Separate flags for first data loading
    private val isGetFirstData = MutableStateFlow(true)
    private val isGetFirstDataFilter = MutableStateFlow(true)

    // Jobs for cancellation
    private var challengesJob: Job? = null
    private var filterJob: Job? = null

    init {
        restartCounterPage()
    }

    fun getAllChallenges() {
        challengesJob?.cancel()
        challengesJob = viewModelScope.launch {
            try {
                Log.d(TAG_CHALLENGES, "Loading challenges - Page: ${_counterPage.value}")

                if (_counterPage.value > _totalPage.value) {
                    Log.d(TAG_CHALLENGES, "All pages loaded")
                    _stateGetChallenges.value = GlobalState.SUCCESS
                    return@launch
                }

                if (_stateGetChallenges.value != GlobalState.LOADING) {
                    _stateGetChallenges.value = GlobalState.LOADING
                }

                val result = homeChallengesUseCase(page = _counterPage.value)

                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!

                    if (body.results.isNotEmpty()) {
                        Log.d(TAG_CHALLENGES, "Success - Loaded ${body.results.size} challenges")

                        // Set total pages only on first load
                        if (isGetFirstData.value) {
                            isGetFirstData.value = false
                            _totalPage.value = body.totalPages
                        }

                        // Add new challenges to existing list
                        _challenges.value = _challenges.value + body.results
                        _counterPage.value = _counterPage.value + 1
                        _stateGetChallenges.value = GlobalState.SUCCESS
                    } else {
                        Log.d(TAG_CHALLENGES, "No more challenges available")
                        _stateGetChallenges.value = GlobalState.EMPTY
                    }
                } else {
                    Log.w(TAG_CHALLENGES, "Request failed or empty response")
                    _stateGetChallenges.value = if (_challenges.value.isEmpty()) GlobalState.EMPTY else GlobalState.SUCCESS
                }

            } catch (e: Exception) {
                Log.e(TAG_CHALLENGES, "Error loading challenges", e)
                _stateGetChallenges.value = GlobalState.ERROR
            }
        }
    }

    fun restartCounterPage() {
        challengesJob?.cancel()
        challengesJob = viewModelScope.launch {
            Log.d(TAG_CHALLENGES, "Restarting challenge loading")

            _stateGetChallenges.value = GlobalState.LOADING
            _counterPage.value = 1
            _totalPage.value = 1
            _challenges.value = emptyList()
            isGetFirstData.value = true

            getAllChallenges()
            _initialState.value = true
        }
    }

    fun removeChallenge(challengeID: String) {
        val initialSize = _challenges.value.size
        _challenges.value = _challenges.value.filterNot { it.challengeId == challengeID }

        if (_challenges.value.size < initialSize) {
            Log.d(TAG_CHALLENGES, "Challenge $challengeID removed")
            if (_challenges.value.isEmpty()) {
                _stateGetChallenges.value = GlobalState.EMPTY
            }
        }
    }

    override fun updateChallenge(challengeID: String, challengeDataUpdate: ChallengeDataUpdate): Boolean {
        var updated = false

        _challenges.update { challenges ->
            challenges.map { item ->
                if (item.challengeId == challengeID) {
                    updated = true
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
            Log.d(TAG_CHALLENGES, "Challenge $challengeID updated successfully")
        }

        return updated
    }

    fun setInitialFilter(filterRequest: FilterRequest) {
        _isFilter.value = true
        _initialFilterRequest.value = filterRequest
        Log.d(TAG_FILTER, "Filter set: $filterRequest")
        resetFilter()
    }

    fun resetFilter() {
        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            Log.d(TAG_FILTER, "Resetting filter")
            _stateFilter.value = GlobalState.LOADING
            _currentPageFilter.value = 1
            _totalPageFilter.value = 1
            _challengesFilter.value = emptyList()
            isGetFirstDataFilter.value = true
            filterChallenges()
        }
    }

    fun discardFilter() {
        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            Log.d(TAG_FILTER, "Discarding filter")
            _currentPageFilter.value = 1
            _totalPageFilter.value = 1
            _challengesFilter.value = emptyList()
            _isFilter.value = false
            _initialFilterRequest.value = null
            isGetFirstDataFilter.value = true
            _stateFilter.value = GlobalState.IDLE
        }
    }

    fun filterChallenges() {
        val currentFilter = _initialFilterRequest.value
        if (currentFilter == null) {
            Log.w(TAG_FILTER, "No filter request available")
            return
        }

        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            try {
                Log.d(TAG_FILTER, "Loading filtered challenges - Page: ${_currentPageFilter.value}")

                if (_currentPageFilter.value > _totalPageFilter.value) {
                    Log.d(TAG_FILTER, "All filter pages loaded")
                    _stateFilter.value = GlobalState.SUCCESS
                    return@launch
                }

                if (_stateFilter.value != GlobalState.LOADING) {
                    _stateFilter.value = GlobalState.LOADING
                }

                val result = homeChallengesUseCase.filterChallenge(
                    page = _currentPageFilter.value,
                    filterRequest = currentFilter
                )

                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!

                    if (body.results.isNotEmpty()) {
                        Log.d(TAG_FILTER, "Filter success - Loaded ${body.results.size} challenges")

                        // Set total pages only on first load
                        if (isGetFirstDataFilter.value) {
                            isGetFirstDataFilter.value = false
                            _totalPageFilter.value = body.totalPages
                        }

                        _challengesFilter.value = _challengesFilter.value + body.results
                        _currentPageFilter.value = _currentPageFilter.value + 1
                        _stateFilter.value = GlobalState.SUCCESS
                    } else {
                        Log.d(TAG_FILTER, "No more filtered challenges available")
                        _stateFilter.value = GlobalState.EMPTY
                    }
                } else {
                    Log.w(TAG_FILTER, "Filter request failed or empty response")
                    _stateFilter.value = if (_challengesFilter.value.isEmpty()) GlobalState.EMPTY else GlobalState.SUCCESS
                }

            } catch (e: Exception) {
                Log.e(TAG_FILTER, "Error filtering challenges", e)
                _stateFilter.value = GlobalState.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        challengesJob?.cancel()
        filterJob?.cancel()
    }

    companion object {
        private const val TAG_CHALLENGES = "Challenges"
        private const val TAG_FILTER = "Filter Challenge"
    }
}
package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.model.PreviewDataUser
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.useCase.ChallengesUserUseCase
import com.example.myapplication.utils.StateGetChallenges
import com.example.myapplication.utils.StatePreviewDataUser
import com.example.myapplication.utils.UpdateChallenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengesUserViewModel @Inject constructor(
    private val challengesUserUseCase: ChallengesUserUseCase,
    private val localTokenManager: TokenManager,
): UpdateChallenge, ViewModel() {

    private val _challenges = MutableStateFlow<List<Challenge>>(emptyList())
    val challenges: StateFlow<List<Challenge>> = _challenges

    private val _totalPage = MutableStateFlow<Int>(1)
    val totalPage: StateFlow<Int> = _totalPage

    private val _counterPage = MutableStateFlow<Int>(1)
    val counterPage: StateFlow<Int> = _counterPage

    private val _initialState = MutableStateFlow<Boolean>(false)
    val initialState : StateFlow<Boolean> = _initialState

    private val _stateGetChallenges = MutableStateFlow<StateGetChallenges>(StateGetChallenges.Idle)
    val stateGetChallenges: StateFlow<StateGetChallenges> = _stateGetChallenges

    init {
        viewModelScope.launch {
            _stateGetChallenges.value = StateGetChallenges.Loading
            totalPage()
            getAllChallengesUser()
            _initialState.value = true

        }


    }


    fun getAllChallengesUser() {
        if (_stateGetChallenges.value == StateGetChallenges.Loading) StateGetChallenges.Loading

        if (counterPage.value > totalPage.value) return

        viewModelScope.launch {
            _stateGetChallenges.value = StateGetChallenges.Loading
            Log.d("getAllChallenges", " Loading Challenges for page ${counterPage.value}")

            try {
                val result = challengesUserUseCase(page = counterPage.value, userId = localTokenManager.getUserId().toString())

                when {
                    result.isSuccessful && result.body()!!.data.isNullOrEmpty() && counterPage.value == 1 -> {
                        _stateGetChallenges.value = StateGetChallenges.NULL
                        Log.d("getAllChallenges", " No posts found (page = 1)")
                    }

                    result.isSuccessful && result.body() != null -> {
                        val newPosts = result.body()!!.data

                        val uniquePosts = newPosts.filter { newItem ->
                            _challenges.value.none { it.challengeID == newItem.challengeID }
                        }

                        _challenges.value = _challenges.value + uniquePosts

                        _stateGetChallenges.value = StateGetChallenges.Success(data = _challenges.value)

                        _counterPage.value = counterPage.value + 1

                        Log.d("getAllChallenges", " Success: loaded ${uniquePosts.size} unique Challenges")
                    }

                    result.isSuccessful && result.body() == null -> {
                        _stateGetChallenges.value = StateGetChallenges.Failure(data = emptyList())
                        Log.w("getAllChallenges", " Response successful but body is null")
                    }

                    result.body()!!.statusCode==401 -> {
                        _stateGetChallenges.value = StateGetChallenges.Failure(data = emptyList())
                        Log.e("getAllChallenges", " Failed response: ${result.code()} - ${result.message()}")
                    }
                }

            } catch (e: Exception) {
                _stateGetChallenges.value = StateGetChallenges.Failure(data = emptyList())
                Log.e("getAllChallenges", " Exception thrown: ${e.localizedMessage}", e)
            }
        }
    }

    fun restartCounterPage() {
        viewModelScope.launch {
            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 1")
            _stateGetChallenges.value = StateGetChallenges.Loading

            _challenges.value = emptyList()
            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 2")

            _counterPage.value = 1
            _totalPage.value = 1

            totalPage()
            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 3")

            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 4")

            getAllChallengesUser()

            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 5")

        }
    }

    fun totalPage(){

        viewModelScope.launch {
            try {

                val totalPageResponse = challengesUserUseCase.totalPageUserPosts(userId = localTokenManager.getUserId().toString())

                if (totalPageResponse.isSuccessful && totalPageResponse.body() != null || totalPageResponse.body()!! > 0){
                    _totalPage.value = totalPageResponse.body() as Int
                }else{
                    _totalPage.value = 0
                }

            }catch (e: Exception){
                _totalPage.value = 0
            }
        }
    }

    // Remove a challenge by its ID from the current list and update the state if the list becomes empty
    fun removeChallenge(challengeID: String) {
        _challenges.value = _challenges.value.filterNot { it.challengeID == challengeID }
        if (_challenges.value.isEmpty()) {
            _stateGetChallenges.value = StateGetChallenges.NULL
        }
    }

    override fun updateChallenge(challengeID: String, challengeRequest: ChallengeRequest): Boolean {
        var updated = false

        _challenges.update { challenges ->
            challenges.map { item ->
                if (item.challengeID == challengeID) {
                    updated = true
                    item.copy(
                        description = challengeRequest.descriptionPost,
                        club = challengeRequest.club,
                        whatsUpNumber = challengeRequest.whatsUpNumber,
                        gender = challengeRequest.gender,
                        team = challengeRequest.team,
                        governorate = challengeRequest.governorate,
                        city = challengeRequest.city
                    )
                } else {
                    item // مهم ترجع العنصر كما هو إذا لم ينطبق الشرط
                }
            }
        }

        return updated
    }

}



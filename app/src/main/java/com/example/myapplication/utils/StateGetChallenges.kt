package com.example.myapplication.utils

import com.example.myapplication.domain.model.challenge_model.Challenge

sealed class StateGetChallenges {
    object Idle : StateGetChallenges()
    object Loading : StateGetChallenges()
    object NULL : StateGetChallenges()
    data class Success(val data: List<Challenge>) : StateGetChallenges()
    data class Failure(val data: List<Challenge>) : StateGetChallenges()
}

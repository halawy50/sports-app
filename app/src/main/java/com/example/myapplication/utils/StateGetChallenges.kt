package com.example.myapplication.utils

import com.example.myapplication.domain.model.challenge_model.Challenge

sealed class StateGetPosts {
    object Idle : StateGetPosts()
    object Loading : StateGetPosts()
    object NULL : StateGetPosts()
    data class Success(val data: List<Challenge>) : StateGetPosts()
    data class Failure(val data: List<Challenge>) : StateGetPosts()
}

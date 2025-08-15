package com.example.myapplication.utils

import com.example.myapplication.domain.model.challenge_model.ResponseData

sealed class StateUpdateChallenge {
    object Idle : StateUpdateChallenge()
    object Loading : StateUpdateChallenge()
    data class Success(val data: ResponseData) : StateUpdateChallenge()
    data class Failure(val data: ResponseData) : StateUpdateChallenge()
    object UnAuthorization : StateUpdateChallenge()
}

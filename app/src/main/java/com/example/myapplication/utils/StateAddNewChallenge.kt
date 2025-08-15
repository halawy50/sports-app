package com.example.myapplication.utils

import com.example.myapplication.domain.model.challenge_model.ResponseData

sealed class StateAddNewChallenge {
    object Idle : StateAddNewChallenge()
    object Loading : StateAddNewChallenge()
    data class Success(val data: ResponseData) : StateAddNewChallenge()
    data class Failure(val data: ResponseData) : StateAddNewChallenge()
    object UnAuthorization : StateAddNewChallenge()
}

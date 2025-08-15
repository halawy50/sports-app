package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import retrofit2.Response

interface AddNewChallengeRepository {
    suspend fun addNewChallenge(
        token: String,
        challengeRequest: ChallengeRequest
    ): Response<ResponseData>

}
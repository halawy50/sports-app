package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.model.challenge_model.SingleChallenge
import retrofit2.Response

interface UpdateChallengeRepository {
    suspend fun updateChallenge(challengeId: String, accessToken: String, challengeRequest: ChallengeRequest): Response<ResponseData>
    suspend fun getSingleChallenge(challengeId: String): Response<SingleChallenge>
}
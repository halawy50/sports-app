package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import retrofit2.Response

interface UpdateChallengeRepository {
    suspend fun updateChallenge(postId: String, accessToken: String, challengeRequest: ChallengeRequest): Response<ResponseData>
    suspend fun getSingleChallenge(postId: String): Response<Challenge>
}
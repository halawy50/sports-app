package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.challenge_model.ResponseData
import retrofit2.Response

interface DeleteChallengeRepository {
    suspend fun deleteChallenge(challengeID: String, accessToken: String): Response<ResponseData>
}
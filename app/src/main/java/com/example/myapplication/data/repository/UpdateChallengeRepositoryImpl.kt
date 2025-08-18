package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.model.challenge_model.SingleChallenge
import com.example.myapplication.domain.repository.UpdateChallengeRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateChallengeRepositoryImpl @Inject constructor(private val challengeService: ChallengeService):UpdateChallengeRepository {

    override suspend fun updateChallenge(
        challengeId: String,
        accessToken: String,
        challengeRequest: ChallengeRequest
    ): Response<ResponseData> {
        return challengeService.updateChallenge(
            postId = challengeId,
            accessToken = accessToken,
            challengeRequest = challengeRequest
        )
    }

    override suspend fun getSingleChallenge(challengeId: String): Response<SingleChallenge> {
        return challengeService.getSingleChallenge(challengeId)
    }
}
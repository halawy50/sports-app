package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.repository.DeleteChallengeRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteChallengeRepositoryImpl @Inject constructor(private val challengeService: ChallengeService): DeleteChallengeRepository {
    override suspend fun deleteChallenge(
        challengeID: String,
        accessToken: String
    ): Response<ResponseData> {
        return challengeService.deleteChallenge(challengeID = challengeID, accessToken = accessToken)
    }
}
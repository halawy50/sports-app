package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.repository.AddNewChallengeRepository
import retrofit2.Response
import javax.inject.Inject

class AddNewChallengeRepositoryImpl @Inject constructor(private val challengeService: ChallengeService): AddNewChallengeRepository {

    override suspend fun addNewChallenge(
        token: String,
        challengeRequest: ChallengeRequest
    ): Response<ResponseData> {
        return challengeService.addNewChallenge(
            accessToken = token,
            challengeRequest = challengeRequest
        )
    }


}
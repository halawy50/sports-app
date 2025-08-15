package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.domain.model.PreviewDataUser
import com.example.myapplication.domain.model.challenge_model.ChallengeUserResponse
import com.example.myapplication.domain.repository.ChallengesUserRepository
import retrofit2.Response
import javax.inject.Inject

class ChallengeUserRepositoryImpl @Inject constructor(private val challengeService: ChallengeService) : ChallengesUserRepository {
    override suspend fun getChallengesUser(
        userId: String,
        page: Int
    ): Response<ChallengeUserResponse> {
        return challengeService.getChallengesUser(userId = userId, page = page)
    }

    override suspend fun totalPageUserChallenges(userId: String): Response<Int> {
        return challengeService.totalPageUserChallenges(userId = userId)
    }

    override suspend fun previewDataUser(userId: String): Response<PreviewDataUser> {
        return challengeService.previewDataUser(userId = userId)
    }
}
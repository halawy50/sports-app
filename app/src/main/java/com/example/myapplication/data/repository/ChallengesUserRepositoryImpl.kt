package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.domain.model.challenge_model.ChallengeResponsePage
import com.example.myapplication.domain.repository.ChallengesUserRepository
import retrofit2.Response
import javax.inject.Inject

class ChallengesUserRepositoryImpl @Inject constructor(private val challengeService: ChallengeService) : ChallengesUserRepository {
    override suspend fun getChallengesUser(
        userId: String,
        page: Int
    ): Response<ChallengeResponsePage> {
        return challengeService.getChallengesUser(userId = userId, page = page)
    }
}
package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.domain.model.challenge_model.ChallengeResponsePage
import com.example.myapplication.domain.repository.HomeChallengesRepository
import retrofit2.Response
import javax.inject.Inject

class HomeChallengesRepositoryImpl @Inject constructor(private val challengeService: ChallengeService) : HomeChallengesRepository {

    override suspend fun getChallenges(page: Int): Response<ChallengeResponsePage> {
        return challengeService.getAllChallenges(page)
    }

    override suspend fun filterChallenge(page: Int, filterRequest: FilterRequest
    ): Response<ChallengeResponsePage> {
        return challengeService.filterChallenge(page = page, filterRequest = filterRequest)
    }

}
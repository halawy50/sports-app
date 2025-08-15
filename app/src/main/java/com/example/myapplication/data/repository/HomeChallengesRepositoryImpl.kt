package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.domain.repository.HomeChallengesRepository
import com.example.persentation.domain.data.post_model.FilterResponse
import retrofit2.Response
import javax.inject.Inject

class HomeChallengesRepositoryImpl @Inject constructor(private val challengeService: ChallengeService) : HomeChallengesRepository {

    override suspend fun getChallenges(page: Int): Response<List<Challenge>> {
        return challengeService.getChallenge(page)
    }

    override suspend fun totalPage(): Response<Int> {
        return challengeService.totalPage()
    }

    override suspend fun filterChallenge(page: Int, filterRequest: FilterRequest
    ): Response<FilterResponse> {
        return challengeService.filterChallenge(page = page, filterRequest = filterRequest)
    }

}
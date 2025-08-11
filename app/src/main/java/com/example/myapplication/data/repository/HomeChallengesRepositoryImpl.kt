package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.repository.HomePostRepository
import retrofit2.Response
import javax.inject.Inject

class HomePostRepositoryImpl @Inject constructor(private val challengeService: ChallengeService) : HomePostRepository {

    override suspend fun getPosts(page: Int): Response<List<Challenge>> {
        return challengeService.getChallenge(page)
    }

    override suspend fun totalPage(): Response<Int> {
        return challengeService.totalPage()
    }

}
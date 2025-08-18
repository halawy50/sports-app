package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.challenge_model.ChallengeResponsePage
import retrofit2.Response

interface ChallengesUserRepository {

    suspend fun getChallengesUser(
        userId: String,
        page: Int

    ): Response<ChallengeResponsePage>
}
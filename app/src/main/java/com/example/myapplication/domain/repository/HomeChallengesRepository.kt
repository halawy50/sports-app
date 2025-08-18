package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.domain.model.challenge_model.ChallengeResponsePage
import retrofit2.Response

interface HomeChallengesRepository {

    suspend fun getChallenges(page: Int): Response<ChallengeResponsePage>
    suspend fun filterChallenge(page: Int, filterRequest: FilterRequest): Response<ChallengeResponsePage>

}
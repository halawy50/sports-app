package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.persentation.domain.data.post_model.FilterResponse
import retrofit2.Response

interface HomeChallengesRepository {

    suspend fun getChallenges(page: Int): Response<List<Challenge>>
    suspend fun totalPage(): Response<Int>
    suspend fun filterChallenge(page: Int, filterRequest: FilterRequest): Response<FilterResponse>

}
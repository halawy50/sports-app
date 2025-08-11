package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.challenge_model.Challenge
import retrofit2.Response

interface HomePostRepository {

    suspend fun getPosts(page: Int): Response<List<Challenge>>
    suspend fun totalPage(): Response<Int>

}
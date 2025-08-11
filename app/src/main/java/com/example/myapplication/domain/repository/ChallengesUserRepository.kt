package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.PreviewDataUser
import com.example.myapplication.domain.model.challenge_model.ChallengeUserResponse
import retrofit2.Response

interface PostsUserRepository {

    suspend fun getPostsUser(
        userId: String,
        page: Int

    ): Response<ChallengeUserResponse>

    suspend fun totalPageUserPosts(
        userId: String,
    ): Response<Int>

    suspend fun previewDataUser(
        userId: String,
    ): Response<PreviewDataUser>
}
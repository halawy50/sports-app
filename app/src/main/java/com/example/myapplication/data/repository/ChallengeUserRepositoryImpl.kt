package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.PostService
import com.example.myapplication.domain.model.PreviewDataUser
import com.example.myapplication.domain.model.post_model.PostsUserResponse
import com.example.myapplication.domain.repository.PostsUserRepository
import retrofit2.Response
import javax.inject.Inject

class PostsUserRepositoryImpl @Inject constructor(private val postService: PostService) : PostsUserRepository {
    override suspend fun getPostsUser(
        userId: String,
        page: Int
    ): Response<PostsUserResponse> {
        return postService.getPostsUser(userId = userId, page = page)
    }

    override suspend fun totalPageUserPosts(userId: String): Response<Int> {
        return postService.totalPageUserPosts(userId = userId)
    }

    override suspend fun previewDataUser(userId: String): Response<PreviewDataUser> {
        return postService.previewDataUser(userId = userId)
    }
}
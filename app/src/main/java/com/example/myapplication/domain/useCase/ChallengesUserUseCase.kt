package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.PostsUserRepository

class PostsUserUseCase(private val postsUserRepository: PostsUserRepository) {

    suspend operator fun invoke(
        userId: String,
        page: Int
    ) = postsUserRepository.getPostsUser(userId = userId, page = page)

    suspend fun totalPageUserPosts(
        userId: String,
    ) = postsUserRepository.totalPageUserPosts(userId = userId)

    suspend fun previewDataUser(userId: String) = postsUserRepository.previewDataUser(userId = userId)

}
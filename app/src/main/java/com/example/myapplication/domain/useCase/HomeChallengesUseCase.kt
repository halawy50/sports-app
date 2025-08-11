package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.HomePostRepository

class HomePostUseCase(private val homePostRepository: HomePostRepository) {
    suspend operator fun invoke(page: Int) =  homePostRepository.getPosts(page = page)
    suspend fun totalPage() =  homePostRepository.totalPage()
}
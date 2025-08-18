package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.ChallengesUserRepository

class ChallengesUserUseCase(private val challengesUserRepository: ChallengesUserRepository) {

    suspend operator fun invoke(
        userId: String,
        page: Int
    ) = challengesUserRepository.getChallengesUser(userId = userId, page = page)

}
package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.repository.AddNewChallengeRepository

class AddNewChallengeUseCase(private val addNewChallengeRepository: AddNewChallengeRepository) {
    suspend operator fun invoke(token: String, challengeRequest: ChallengeRequest) = addNewChallengeRepository.addNewChallenge(
        token = token,
        challengeRequest = challengeRequest
    )
}
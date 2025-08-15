package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.DeleteChallengeRepository

class DeleteChallengeUseCase(private val deleteChallengeRepository: DeleteChallengeRepository) {
    suspend operator fun invoke(challengeId: String, accessToken: String) = deleteChallengeRepository.deleteChallenge(challengeID = challengeId, accessToken = accessToken)
}
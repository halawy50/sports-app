package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.repository.UpdateChallengeRepository


class UpdateChallengeUseCase(private val updateChallengeRepository: UpdateChallengeRepository) {
    suspend operator fun invoke(postID: String, challengeRequest: ChallengeRequest, accessToken: String) =
        updateChallengeRepository.updateChallenge(postId = postID, challengeRequest = challengeRequest, accessToken = accessToken)

    suspend fun getSingleChallenge(challengeID: String) = updateChallengeRepository.getSingleChallenge(postId = challengeID)
}
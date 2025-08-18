package com.example.myapplication.utils

import com.example.myapplication.domain.model.challenge_model.ChallengeDataUpdate

interface UpdateChallenge {
    fun updateChallenge(challengeID: String, challengeDataUpdate: ChallengeDataUpdate): Boolean
}
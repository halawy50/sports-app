package com.example.myapplication.utils

import com.example.myapplication.domain.model.challenge_model.ChallengeRequest

interface UpdateChallenge {
    fun updateChallenge(challengeID :String, challengeRequest: ChallengeRequest): Boolean
}
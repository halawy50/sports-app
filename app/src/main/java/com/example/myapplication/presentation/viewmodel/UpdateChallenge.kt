package com.example.myapplication.presentation.viewmodel

import com.example.myapplication.domain.model.challenge_model.ChallengeRequest

interface UpdateChallenge {
    fun updateChallenge(challengeID :String, challengeRequest: ChallengeRequest): Boolean
}
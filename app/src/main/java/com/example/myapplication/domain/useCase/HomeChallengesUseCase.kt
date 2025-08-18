package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.domain.repository.HomeChallengesRepository

class HomeChallengesUseCase(private val homeChallengesRepository: HomeChallengesRepository) {
    suspend operator fun invoke(page: Int) =  homeChallengesRepository.getChallenges(page = page)
    suspend fun filterChallenge(page: Int, filterRequest: FilterRequest)
    =  homeChallengesRepository.filterChallenge(page = page, filterRequest = filterRequest )
}
package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.domain.repository.HomeChallengesRepository
import com.example.persentation.domain.data.post_model.FilterResponse
import retrofit2.Response

class HomeChallengesUseCase(private val homeChallengesRepository: HomeChallengesRepository) {
    suspend operator fun invoke(page: Int) =  homeChallengesRepository.getChallenges(page = page)
    suspend fun totalPage() =  homeChallengesRepository.totalPage()
    suspend fun filterChallenge(page: Int, filterRequest: FilterRequest)
    =  homeChallengesRepository.filterChallenge(page = page, filterRequest = filterRequest )
}
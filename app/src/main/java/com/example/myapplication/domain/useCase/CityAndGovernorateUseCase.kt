package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.CityAndGovernorateRepository

class CityAndGovernorateUseCase(private val cityAndGovernorateRepository: CityAndGovernorateRepository) {
    suspend fun getGovernorate() = cityAndGovernorateRepository.getGovernorate()
    suspend fun getCities(governorateId: String) = cityAndGovernorateRepository.getCities(governorateId = governorateId)
}
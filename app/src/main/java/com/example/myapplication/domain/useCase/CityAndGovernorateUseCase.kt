package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.CityAndGovernorateRepository

class CityAndGovernorateUseCase(private val cityAndGovernorateRepository: CityAndGovernorateRepository) {
    suspend fun getGovernorate() = cityAndGovernorateRepository.getGovernorates()

    suspend fun getCities(governorateId: String) = cityAndGovernorateRepository.getCities(governorateId = governorateId)

    // Fetch a single city by ID
    suspend fun getCityById(id: String) = cityAndGovernorateRepository.getCityById(id = id)

    // Fetch a single governorate by ID
    suspend fun getGovernorateById(id: String) = cityAndGovernorateRepository.getGovernorateById(id)


}
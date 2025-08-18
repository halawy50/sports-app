package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Governorate
import retrofit2.Response

interface CityAndGovernorateRepository {

    // Fetch all governorates
    suspend fun getGovernorates(): Response<List<Governorate>>

    // Fetch a single governorate by its ID
    suspend fun getGovernorateById(id: String): Response<Governorate>

    // Fetch all cities under a specific governorate
    suspend fun getCities(governorateId: String): Response<List<City>>

    // Fetch a single city by its ID
    suspend fun getCityById(id: String): Response<City>
}

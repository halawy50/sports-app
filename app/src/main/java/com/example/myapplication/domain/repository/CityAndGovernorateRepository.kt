package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Governorate
import retrofit2.Response

interface CityAndGovernorateRepository {
    suspend fun getGovernorate(): Response<List<Governorate>>
    suspend fun getCities(governorateId: String) : Response<List<City>>
}
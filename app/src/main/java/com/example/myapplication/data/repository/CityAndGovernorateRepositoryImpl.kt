package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.apiService.CityAndGovernorateService
import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Governorate
import com.example.myapplication.domain.repository.CityAndGovernorateRepository
import retrofit2.Response
import javax.inject.Inject

class CityAndGovernorateRepositoryImpl @Inject constructor(private val cityAndGovernorateService: CityAndGovernorateService): CityAndGovernorateRepository {
    override suspend fun getGovernorate(): Response<List<Governorate>> {
        return cityAndGovernorateService.getGovernorates()
    }

    override suspend fun getCities(governorateId: String): Response<List<City>> {
        return cityAndGovernorateService.getCities(governorateId = governorateId)
    }
}
package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.CityAndGovernorateService
import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Governorate
import com.example.myapplication.domain.repository.CityAndGovernorateRepository
import retrofit2.Response
import javax.inject.Inject

class CityAndGovernorateRepositoryImpl @Inject constructor(
    private val cityAndGovernorateService: CityAndGovernorateService
) : CityAndGovernorateRepository {

    // Fetch all governorates from the API
    override suspend fun getGovernorates(): Response<List<Governorate>> {
        return cityAndGovernorateService.getGovernorates()
    }

    // Fetch all cities for a specific governorate
    override suspend fun getCities(governorateId: String): Response<List<City>> {
        return cityAndGovernorateService.getCities(governorateId)
    }

    // Optional: Fetch a single governorate by ID
    override suspend fun getGovernorateById(id: String): Response<Governorate> {
        val all = cityAndGovernorateService.getGovernorates().body()
        val governorate = all?.find { it.id == id }
        return Response.success(governorate)
    }

    // Optional: Fetch a single city by ID
    override suspend fun getCityById(id: String): Response<City> {
        val allCities = mutableListOf<City>()
        cityAndGovernorateService.getGovernorates().body()?.forEach { governorate ->
            val cities = cityAndGovernorateService.getCities(governorate.id).body()
            if (cities != null) allCities.addAll(cities)
        }
        val city = allCities.find { it.id == id }
        return Response.success(city)
    }
}

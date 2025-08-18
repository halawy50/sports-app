package com.example.myapplication.data.apiService

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Governorate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CityAndGovernorateService {

    // Get all governorates
    @GET("governorates")
    suspend fun getGovernorates(): Response<List<Governorate>>

    // Get a single governorate by ID
    @GET("governorates/{id}")
    suspend fun getGovernorate(@Path("id") id: String): Response<Governorate>

    // Get all cities for a specific governorate
    @GET("cities/{governorateId}")
    suspend fun getCities(@Path("governorateId") governorateId: String): Response<List<City>>

    // Get a single city by ID
    @GET("cities/single/{id}")
    suspend fun getCity(@Path("id") id: String): Response<City>

}

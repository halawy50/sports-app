package com.example.myapplication.data.apiService

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Governorate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CityAndGovernorateService {
    @GET("governorates")
    suspend fun getGovernorates() : Response<List<Governorate>>

    @GET("cities/{governorateId}")
    suspend fun getCities(@Path("governorateId") governorateId: String) : Response<List<City>>
}
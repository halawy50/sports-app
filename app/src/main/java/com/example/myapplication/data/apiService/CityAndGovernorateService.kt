package com.example.myapplication.data.apiService

import com.example.myapplication.domain.model.GovernorateListWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CItyService {
    @GET("governorates")
    fun getGovernorates() : Response<GovernorateListWrapper>

    @GET("cities/{governorateId}")
    fun getCities(@Path("governorateId") governorateId: String) : Response<GovernorateListWrapper>
}
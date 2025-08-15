package com.example.myapplication.presentation.di.city_and_governorate_module

import com.example.myapplication.data.apiService.CityAndGovernorateService
import com.example.myapplication.data.repository.CityAndGovernorateRepositoryImpl
import com.example.myapplication.domain.repository.CityAndGovernorateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryCityAndGovernorateModule {

    //CityAndGovernorate Repository
    @Provides
    @Singleton
    fun provideCityAndGovernorateRepository(cityAndGovernorateService: CityAndGovernorateService): CityAndGovernorateRepository {
        return CityAndGovernorateRepositoryImpl(cityAndGovernorateService = cityAndGovernorateService)
    }

}
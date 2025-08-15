package com.example.myapplication.presentation.di.city_and_governorate_module

import com.example.myapplication.domain.repository.CityAndGovernorateRepository
import com.example.myapplication.domain.useCase.CityAndGovernorateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseCityAndGovernorateModule {

    //CityAndGovernorate UseCase
    @Provides
    @Singleton
    fun providesUseCityAndGovernorate(cityAndGovernorateRepository: CityAndGovernorateRepository): CityAndGovernorateUseCase{
        return CityAndGovernorateUseCase(cityAndGovernorateRepository)
    }

}
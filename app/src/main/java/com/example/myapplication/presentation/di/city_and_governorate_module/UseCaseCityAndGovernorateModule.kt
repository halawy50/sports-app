package com.example.myapplication.presentation.di.city_and_governorate

import com.example.myapplication.domain.repository.CityAndGovernorateRepository
import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.domain.repository.ResetPasswordRepository
import com.example.myapplication.domain.repository.SendOTPRepository
import com.example.myapplication.domain.repository.VerifyCodeRepository
import com.example.myapplication.domain.useCase.CityAndGovernorateUseCase
import com.example.myapplication.domain.useCase.LoginUseCase
import com.example.myapplication.domain.useCase.RegisterUseCase
import com.example.myapplication.domain.useCase.ResetPasswordUseCase
import com.example.myapplication.domain.useCase.SendOTPUseCase
import com.example.myapplication.domain.useCase.VerifyCodeUseCase
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
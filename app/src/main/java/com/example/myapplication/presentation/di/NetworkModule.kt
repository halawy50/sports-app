package com.example.myapplication.presentation.di

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.data.apiService.CityAndGovernorateService
import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.data.apiService.TokenService
import com.example.myapplication.presentation.constant.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp():OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(20 , TimeUnit.SECONDS)
            .readTimeout(20 , TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BaseUrl.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideCityAndGovernorateService(retrofit: Retrofit) : CityAndGovernorateService {
        return retrofit.create(CityAndGovernorateService::class.java)
    }

    @Provides
    @Singleton
    fun provideChallengeService(retrofit: Retrofit) : ChallengeService {
        return retrofit.create(ChallengeService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenService(retrofit: Retrofit) : TokenService {
        return retrofit.create(TokenService::class.java)
    }
}
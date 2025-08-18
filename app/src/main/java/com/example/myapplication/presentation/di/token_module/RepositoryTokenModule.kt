package com.example.myapplication.presentation.di.token_module

import android.content.Context
import com.example.myapplication.data.apiService.TokenService
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.data.repository.TokenRepositoryImpl
import com.example.myapplication.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryTokenModule {

    //TokenRepository
    @Provides
    @Singleton
    fun provideTokenRepository(tokenService: TokenService): TokenRepository {
        return TokenRepositoryImpl(tokenService = tokenService)
    }

}
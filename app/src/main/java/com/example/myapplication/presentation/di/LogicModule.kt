package com.example.myapplication.presentation.di

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.data.repository.AuthRepositoryImpl
import com.example.myapplication.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LogicModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository{
        return AuthRepositoryImpl(authService = authService)
    }
}
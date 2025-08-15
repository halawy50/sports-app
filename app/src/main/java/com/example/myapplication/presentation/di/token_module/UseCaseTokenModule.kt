package com.example.myapplication.presentation.di.token_module

import com.example.myapplication.domain.repository.TokenRepository
import com.example.myapplication.domain.useCase.TokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseTokenModule {

    //Token UseCase
    @Provides
    @Singleton
    fun providesTokenUseCase(tokenRepository: TokenRepository): TokenUseCase{
        return TokenUseCase(tokenRepository = tokenRepository)
    }


}
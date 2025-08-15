package com.example.myapplication.presentation.di.challenge_module

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.data.repository.AddNewChallengeRepositoryImpl
import com.example.myapplication.data.repository.HomeChallengesRepositoryImpl
import com.example.myapplication.data.repository.ChallengeUserRepositoryImpl
import com.example.myapplication.data.repository.DeleteChallengeRepositoryImpl
import com.example.myapplication.data.repository.UpdateChallengeRepositoryImpl
import com.example.myapplication.domain.repository.AddNewChallengeRepository
import com.example.myapplication.domain.repository.HomeChallengesRepository
import com.example.myapplication.domain.repository.ChallengesUserRepository
import com.example.myapplication.domain.repository.DeleteChallengeRepository
import com.example.myapplication.domain.repository.UpdateChallengeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChallengeRepositoryModule {

    @Provides
    @Singleton
    fun provideHomeChallengesRepository(challengeService: ChallengeService): HomeChallengesRepository{
        return HomeChallengesRepositoryImpl(challengeService = challengeService)
    }


    @Provides
    @Singleton
    fun provideChallengesUserRepository(challengeService: ChallengeService): ChallengesUserRepository{
        return ChallengeUserRepositoryImpl(challengeService = challengeService)
    }

    @Provides
    @Singleton
    fun provideAddNewChallengesRepository(challengeService: ChallengeService): AddNewChallengeRepository{
        return AddNewChallengeRepositoryImpl(challengeService = challengeService)
    }

    @Provides
    @Singleton
    fun provideDeleteChallengesRepository(challengeService: ChallengeService): DeleteChallengeRepository{
        return DeleteChallengeRepositoryImpl(challengeService = challengeService)
    }

    @Provides
    @Singleton
    fun provideUpdateChallengesRepository(challengeService: ChallengeService): UpdateChallengeRepository{
        return UpdateChallengeRepositoryImpl(challengeService = challengeService)
    }

}
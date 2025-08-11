package com.example.myapplication.presentation.di.post_module

import com.example.myapplication.data.apiService.ChallengeService
import com.example.myapplication.data.repository.HomePostRepositoryImpl
import com.example.myapplication.data.repository.ChallengeUserRepositoryImpl
import com.example.myapplication.domain.repository.HomePostRepository
import com.example.myapplication.domain.repository.PostsUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostRepositoryModule {

    @Provides
    @Singleton
    fun provideHomePostRepository(challengeService: ChallengeService): HomePostRepository{
        return HomePostRepositoryImpl(challengeService = challengeService)
    }


    @Provides
    @Singleton
    fun providePostsUserRepository(challengeService: ChallengeService): PostsUserRepository{
        return ChallengeUserRepositoryImpl(challengeService = challengeService)
    }
}
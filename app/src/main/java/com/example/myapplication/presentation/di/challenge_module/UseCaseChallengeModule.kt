package com.example.myapplication.presentation.di.post_module

import com.example.myapplication.domain.repository.HomePostRepository
import com.example.myapplication.domain.repository.PostsUserRepository
import com.example.myapplication.domain.useCase.HomePostUseCase
import com.example.myapplication.domain.useCase.PostsUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasePostModule {


    @Provides
    @Singleton
    fun provideHomePostUseCase(homePostRepository: HomePostRepository): HomePostUseCase{
        return HomePostUseCase(homePostRepository = homePostRepository)
    }


    @Provides
    @Singleton
    fun providePostsUserUseCase(postsUserRepository: PostsUserRepository): PostsUserUseCase{
        return PostsUserUseCase(postsUserRepository = postsUserRepository)
    }

}
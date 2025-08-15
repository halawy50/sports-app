package com.example.myapplication.presentation.di.challenge_module

import com.example.myapplication.domain.repository.AddNewChallengeRepository
import com.example.myapplication.domain.repository.HomeChallengesRepository
import com.example.myapplication.domain.repository.ChallengesUserRepository
import com.example.myapplication.domain.repository.DeleteChallengeRepository
import com.example.myapplication.domain.repository.UpdateChallengeRepository
import com.example.myapplication.domain.useCase.AddNewChallengeUseCase
import com.example.myapplication.domain.useCase.HomeChallengesUseCase
import com.example.myapplication.domain.useCase.ChallengesUserUseCase
import com.example.myapplication.domain.useCase.DeleteChallengeUseCase
import com.example.myapplication.domain.useCase.UpdateChallengeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseChallengeModule {


    @Provides
    @Singleton
    fun provideHomeChallengeUseCase(homeChallengesRepository: HomeChallengesRepository): HomeChallengesUseCase{
        return HomeChallengesUseCase(homeChallengesRepository = homeChallengesRepository)
    }


    @Provides
    @Singleton
    fun provideChallengesUserUseCase(challengesUserRepository: ChallengesUserRepository): ChallengesUserUseCase{
        return ChallengesUserUseCase(challengesUserRepository = challengesUserRepository)
    }

    @Provides
    @Singleton
    fun provideAddNewChallengesUseCase(addNewChallengeRepository: AddNewChallengeRepository): AddNewChallengeUseCase{
        return AddNewChallengeUseCase(addNewChallengeRepository = addNewChallengeRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteChallengesUseCase(deleteChallengeRepository: DeleteChallengeRepository): DeleteChallengeUseCase{
        return DeleteChallengeUseCase(deleteChallengeRepository = deleteChallengeRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateChallengesUseCase(updateChallengeRepository: UpdateChallengeRepository): UpdateChallengeUseCase{
        return UpdateChallengeUseCase(updateChallengeRepository = updateChallengeRepository)
    }

}
package com.example.myapplication.presentation.di

import android.content.Context
import com.example.myapplication.presentation.constant.GendersConstant
import com.example.myapplication.presentation.constant.OnBoardingConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideGenderConstant(
        @ApplicationContext context: Context
    ): GendersConstant {
        return GendersConstant(context)
    }

    @Provides
    @Singleton
    fun provideOnBoardingConstant(
        @ApplicationContext context: Context
    ): OnBoardingConstant {
        return OnBoardingConstant(context)
    }
}
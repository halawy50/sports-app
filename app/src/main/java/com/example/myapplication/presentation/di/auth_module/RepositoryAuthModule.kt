package com.example.myapplication.presentation.di.auth_module

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.data.repository.InformationUserRepositoryImpl
import com.example.myapplication.data.repository.LogOutRepositoryImpl
import com.example.myapplication.data.repository.LoginRepositoryImpl
import com.example.myapplication.data.repository.RegisterRepositoryImpl
import com.example.myapplication.data.repository.ResetPasswordRepositoryImpl
import com.example.myapplication.data.repository.SendOTPRepositoryImpl
import com.example.myapplication.data.repository.VerifyCodeRepositoryImpl
import com.example.myapplication.domain.repository.InformationUserRepository
import com.example.myapplication.domain.repository.LogOutRepository
import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.domain.repository.ResetPasswordRepository
import com.example.myapplication.domain.repository.SendOTPRepository
import com.example.myapplication.domain.repository.VerifyCodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryAuthModule {

    //Register Repository
    @Provides
    @Singleton
    fun provideRegisterRepository(authService: AuthService): RegisterRepository {
        return RegisterRepositoryImpl(authService = authService)
    }

    //Register Repository
    @Provides
    @Singleton
    fun provideLoginRepository(authService: AuthService): LoginRepository {
        return LoginRepositoryImpl(authService = authService)
    }

    //SendOTP Repository
    @Provides
    @Singleton
    fun provideSendOTPRepository(authService: AuthService): SendOTPRepository {
        return SendOTPRepositoryImpl(authService = authService)
    }

    //VerifyCode Repository
    @Provides
    @Singleton
    fun provideVerifyCodeRepository(authService: AuthService): VerifyCodeRepository {
        return VerifyCodeRepositoryImpl(authService = authService)
    }

    //ResetPassword Repository
    @Provides
    @Singleton
    fun provideResetPasswordRepository(authService: AuthService): ResetPasswordRepository {
        return ResetPasswordRepositoryImpl(authService = authService)
    }

    //InformationUser Repository
    @Provides
    @Singleton
    fun provideInformationUserRepository(authService: AuthService): InformationUserRepository {
        return InformationUserRepositoryImpl(authService = authService)
    }

    //LogOut Repository
    @Provides
    @Singleton
    fun provideLogOutRepository(authService: AuthService): LogOutRepository {
        return LogOutRepositoryImpl(authService = authService)
    }
}
package com.example.myapplication.presentation.di.auth_di

import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.domain.repository.ResetPasswordRepository
import com.example.myapplication.domain.repository.SendOTPRepository
import com.example.myapplication.domain.repository.VerifyCodeRepository
import com.example.myapplication.domain.useCase.LoginUseCase
import com.example.myapplication.domain.useCase.RegisterUseCase
import com.example.myapplication.domain.useCase.ResetPasswordUseCase
import com.example.myapplication.domain.useCase.SendOTPUseCase
import com.example.myapplication.domain.useCase.VerifyCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseAuthModule {

    //Register UseCase
    @Provides
    @Singleton
    fun providesUseCaseRegister(registerRepository: RegisterRepository): RegisterUseCase{
        return RegisterUseCase(registerRepository = registerRepository)
    }

    //Login UseCase
    @Provides
    @Singleton
    fun providesUseCaseLogin(loginRepository: LoginRepository): LoginUseCase{
        return LoginUseCase(loginRepository= loginRepository)
    }

    //SendOTP UseCase
    @Provides
    @Singleton
    fun providesUseCaseSendOTP(sendOTPRepository: SendOTPRepository): SendOTPUseCase{
        return SendOTPUseCase(sendOTPRepository = sendOTPRepository)
    }

    //Verify UseCase
    @Provides
    @Singleton
    fun providesUseCaseVerifyOTP(verifyCodeRepository: VerifyCodeRepository): VerifyCodeUseCase{
        return VerifyCodeUseCase(verifyCodeRepository = verifyCodeRepository)
    }

    //ResetPassword UseCase
    @Provides
    @Singleton
    fun providesUseCaseResetPassword(resetPasswordRepository: ResetPasswordRepository): ResetPasswordUseCase{
        return ResetPasswordUseCase(resetPasswordRepository = resetPasswordRepository)
    }
}
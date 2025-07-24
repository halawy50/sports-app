package com.example.myapplication.data.apiService

import com.example.myapplication.domain.model.generate_otp.GenerateOTPRequest
import com.example.myapplication.domain.model.generate_otp.GenerateOTPResponse
import com.example.myapplication.domain.model.login_model.LoginRequest
import com.example.myapplication.domain.model.login_model.LoginResponse
import com.example.myapplication.domain.model.register_model.RegisterRequest
import com.example.myapplication.domain.model.register_model.RegisterResponse
import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpRequest
import com.example.myapplication.domain.model.reset_password.NewPasswordAndOtpResponse
import com.example.myapplication.domain.model.verify_otp.VerifyOTPRequest
import com.example.myapplication.domain.model.verify_otp.VerifyOTPResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    //Register
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    //Login
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    //SendOTP
    @POST("sendOTP")
    suspend fun sendOTP(
        @Body sendOTPRequest: GenerateOTPRequest
    ): Response<GenerateOTPResponse>

    //VerifyCode
    @POST("verify_code")
    suspend fun verifyCode(
        @Body verifyOTPRequest: VerifyOTPRequest
    ): Response<VerifyOTPResponse>

    //RePassword
    @POST("reset_password")
    suspend fun resetPassword(
        @Body newPasswordAndOtpRequest: NewPasswordAndOtpRequest
    ): Response<NewPasswordAndOtpResponse>

}
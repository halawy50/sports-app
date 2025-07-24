package com.example.myapplication.presentation.constant.routes

object RoutesAuth {
    val loginPage = "AuthScreen/LoginPage"
    val signUpPage = "AuthScreen/SignUpPage"
    val forgetPasswordPage = "AuthScreen/ForgetPasswordPage"
    val verifyOTPPage = "AuthScreen/VerifyOTPPage/{email}"
    val newPasswordPage = "AuthScreen/ForgetPasswordPage/NewPasswordPage/{email}/{otp}"

    fun verifyOTPWithEmail(email: String): String {
        return "AuthScreen/VerifyOTPPage/${email}"
    }

    fun newPasswordPage(email: String, otp: String): String {
        return "AuthScreen/ForgetPasswordPage/NewPasswordPage/$email/$otp"
    }
}
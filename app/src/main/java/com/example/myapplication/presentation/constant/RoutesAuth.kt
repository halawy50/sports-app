package com.example.myapplication.presentation.constant

object RoutesAuth {
    val loginPage = "AuthScreen/LoginPage"
    val signUpPage = "AuthScreen/SignUpPage"
    val forgetPasswordPage = "AuthScreen/ForgetPasswordPage"
    val verifyOTPPage = "AuthScreen/VerifyOTPPage/{email}"
    val newPasswordPage = "AuthScreen/ForgetPasswordPage/NewPasswordPage"

    // دي دالة هتستخدمها علشان تحط الإيميل في المسار
    fun verifyOTPWithEmail(email: String): String {
        return "AuthScreen/VerifyOTPPage/${email}"
    }
}
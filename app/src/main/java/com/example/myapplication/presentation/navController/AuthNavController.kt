package com.example.myapplication.presentation.navController

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.components.HideStatusBar
import com.example.myapplication.presentation.constant.Routes
import com.example.myapplication.presentation.constant.RoutesAuth
import com.example.myapplication.presentation.screens.authScreen.forgetPasswordPage.ForgetPasswordPage
import com.example.myapplication.presentation.screens.authScreen.loginPage.LoginPage
import com.example.myapplication.presentation.screens.authScreen.newPasswordPage.NewForgetPasswordPage
import com.example.myapplication.presentation.screens.authScreen.signUpPage.SignUpPage
import com.example.myapplication.presentation.screens.authScreen.verfiyOtp.VerifyOTPPage
import com.example.myapplication.presentation.screens.splashScreen.SplashScreen

@Composable
fun AuthNavController(appNavController: NavController , activity: Activity , padding: PaddingValues){
    val authNavController = rememberNavController()

    HideStatusBar(activity)

    NavHost(
            navController = authNavController,
            startDestination = RoutesAuth.loginPage,
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp , vertical = 10.dp)
    ){
        //Login Page
        composable(RoutesAuth.loginPage){
        LoginPage(appNavController = appNavController , authNavController = authNavController)
        }

        //SignUp Page
        composable(RoutesAuth.signUpPage){
            SignUpPage(appNavController = appNavController , authNavController = authNavController)
        }

        //ForgetPassword Page
        composable(RoutesAuth.forgetPasswordPage){
            ForgetPasswordPage(appNavController = appNavController , authNavController = authNavController)
        }

        //VerifyOTP Page
        composable(
            RoutesAuth.verifyOTPPage,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType}
            )
        ){ backStackEntry ->

            val email = backStackEntry.arguments?.getString("email") ?: ""

            VerifyOTPPage(
                email = email,
                authNavController = authNavController
            )

        }

        //NewPassword Page
        composable(RoutesAuth.newPasswordPage){
            NewForgetPasswordPage(appNavController = appNavController , authNavController = authNavController)
        }

    }
}
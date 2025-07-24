package com.example.myapplication.presentation.navController


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.presentation.screens.onBoardingScreen.OnBoardingScreen
import com.example.myapplication.presentation.screens.splashScreen.ChangeLanguageScreen
import com.example.myapplication.presentation.screens.splashScreen.SplashScreen

@SuppressLint("SuspiciousIndentation")
@Composable
fun NavController(activity: MainActivity , padding: PaddingValues){
    val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.splashScreen,
        ){
            //SplashScreen
            composable(Routes.splashScreen){
                SplashScreen(activity = activity, navController = navController)
            }

            //OnBoardingScreen
            composable(Routes.onBoardingScreen){
                OnBoardingScreen(activity = activity ,navController = navController , padding = padding)
            }

            //changeLanguageScreen
            composable(Routes.changeLanguageScreen){
                ChangeLanguageScreen(navController= navController,activity = activity, padding = padding)
            }

            //AuthScreen
            composable(Routes.authScreen){
                AuthNavController(activity = activity , appNavController = navController , padding = padding)
            }

        }

}
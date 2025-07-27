package com.example.myapplication.presentation.navController


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.presentation.screens.main.MainScreen
//import com.example.myapplication.presentation.screens.main.homeScreen.MainScreen
import com.example.myapplication.presentation.screens.onBoardingScreen.OnBoardingScreen
import com.example.myapplication.presentation.screens.changeLanguageScreen.ChangeLanguageScreen
import com.example.myapplication.presentation.screens.splashScreen.SplashScreen

@SuppressLint("SuspiciousIndentation")
@Composable
fun NavController(activity: MainActivity , padding: PaddingValues){
    val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.mainScreen,
        ){
            //SplashScreen
            composable(Routes.splashScreen){
                SplashScreen(activity = activity, navController = navController)
            }

            //changeLanguageScreen
            composable(Routes.changeLanguageScreen){
                ChangeLanguageScreen(navController= navController,activity = activity, padding = padding)
            }


            //OnBoardingScreen
            composable(Routes.onBoardingScreen){
                OnBoardingScreen(activity = activity ,navController = navController , padding = padding)
            }


            //AuthScreen
            composable(Routes.authScreen){
                AuthNavController(activity = activity , appNavController = navController , padding = padding)
            }

            //Main Screen
            composable(Routes.mainScreen){
                MainScreen(activity = activity)
            }

        }

}
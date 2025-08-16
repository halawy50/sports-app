package com.example.myapplication.presentation.navController


import UpdateChallengePage
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.MainActivity
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.presentation.screens.main.MainScreen
import com.example.myapplication.presentation.screens.onBoardingScreen.OnBoardingScreen
import com.example.myapplication.presentation.screens.changeLanguageScreen.ChangeLanguageScreen
import com.example.myapplication.presentation.screens.main.pages.setting_page.pages.AddNewChallengePage
import com.example.myapplication.presentation.screens.main.pages.setting_page.pages.ChallengesUserPage
import com.example.myapplication.presentation.screens.main.pages.setting_page.pages.InformationUserPage
import com.example.myapplication.presentation.screens.main.pages.setting_page.pages.LanguagePage
import com.example.myapplication.presentation.screens.main.pages.setting_page.pages.PrivacyPolicyPage
import com.example.myapplication.presentation.screens.splashScreen.SplashScreen
import com.example.myapplication.presentation.viewmodel.HomeChallengesViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun NavController(activity: MainActivity ,
                  padding: PaddingValues,
                  homeChallengesViewModel: HomeChallengesViewModel = hiltViewModel()
             ){

    val navController = rememberNavController()


        NavHost(
            navController = navController,
            startDestination = Routes.splashScreen,
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
                MainScreen(activity = activity, navController = navController, homeChallengesViewModel = homeChallengesViewModel)
            }

            //My Challenges Page
            composable(Routes.myChallengesPage){
                ChallengesUserPage(navController = navController, homeChallengesViewModel = homeChallengesViewModel)
            }

            //Add New Challenges Page
            composable(Routes.addNewChallengesPage){
                AddNewChallengePage(appNavController = navController, homeChallengesViewModel = homeChallengesViewModel)
            }

            //Information User Page
            composable(Routes.informationUserPage){
                InformationUserPage(navController = navController)
            }

            //Information User Page
            composable(Routes.privacyPolicyPage){
                PrivacyPolicyPage(navController = navController)
            }
            //Update Challenge Page
            composable(
                Routes.updateChallengePage,
                arguments = listOf(
                    navArgument("challengeID") { type = NavType.StringType}
                )
            ){ backStackEntry ->

                val challengeID = backStackEntry.arguments?.getString("challengeID") ?: ""

                UpdateChallengePage(
                    appNavController = navController,
                    homeChallengesViewModel = homeChallengesViewModel,
                    challengeID = challengeID
                )

            }


            //Change Language Page
            composable(Routes.changeLanguagePage){
                LanguagePage(activity = activity ,navController = navController , padding = padding)
            }

        }

}
package com.example.myapplication.presentation.constant.routes

object Routes {
    val splashScreen = "SplashScreen"
    val onBoardingScreen = "OnBoardingScreen"
    val changeLanguageScreen = "ChangeLanguageScreen"
    val authScreen = "AuthScreen"
    val mainScreen = "MainScreen"
    val myChallengesPage = "MyChallengesPage"
    val addNewChallengesPage = "AddNewChallengesPage"
    val updateChallengePage = "UpdateChallengePage/{challengeID}"
    val changeLanguagePage = "ChangeLanguagePage"
    val informationUserPage = "InformationUserPage"
    val privacyPolicyPage = "PrivacyPolicyPage"


    fun challengeID(challengeID: String): String {
        return "UpdateChallengePage/${challengeID}"
    }
}
package com.example.myapplication.presentation.screens.splashScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.components.HideStatusBar
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.ui.theme.almarai_extrabold
import com.example.myapplication.ui.theme.black
import com.example.myapplication.ui.theme.blue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(activity: MainActivity , navController: NavController){

    var visableCirclerProgress by remember { mutableStateOf(false) }
    var visableLogo by remember { mutableStateOf(false) }
    var visableTitle by remember { mutableStateOf(false) }

    HideStatusBar(activity , textIsDark = true)



    LaunchedEffect(Unit) {
        delay(300)
        visableLogo = true
        delay(100)
        visableTitle = true
        delay(500)
        visableCirclerProgress = true
        delay(3000)
        navController.navigate(Routes.changeLanguageScreen){
            popUpTo(Routes.splashScreen){inclusive = true}
        }
    }




    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {



        AnimatedVisibility(
            visible = visableLogo,
            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically),
        ) {
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo.png" ,
                modifier = Modifier.height(100.dp).width(100.dp)
            )
        }

        AnimatedVisibility(
            visible = visableTitle,
            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically),
            ) {
            Text(stringResource(R.string.app_name), style = TextStyle(color = black , fontFamily = almarai_extrabold , fontSize = 18.sp))
        }

        Spacer(Modifier.height(30.dp))

        AnimatedVisibility(
            visible = visableCirclerProgress,
            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically),
        ) {
            CircularProgressIndicator(color = blue)
        }
    }
}
package com.example.myapplication.presentation.screens.authScreen.loginPage

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonWithBorder
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.HideStatusBar
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.components.InputsComponents.InputPassword
import com.example.myapplication.presentation.components.InputsComponents.InputEmail
import com.example.myapplication.presentation.constant.RoutesAuth
import com.example.myapplication.ui.theme.almarai_regular

@Composable
fun LoginPage(authNavController: NavController , appNavController:NavController){

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo) ,
            contentDescription = stringResource(R.string.logo),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(Modifier.height(20.dp))

        HeaderText(text = stringResource(R.string.herader_welcome))

        Spacer(Modifier.height(20.dp))

        ParagraphText(text = stringResource(R.string.paragraph_welcome))

        Spacer(Modifier.height(20.dp))

        //Input Email
        InputEmail(
            getEmail = { email->

            }
        )


        //Input Password
        InputPassword(
            getPassword = { password->

            }
        )

        //Forget Password Button
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                authNavController.navigate(RoutesAuth.forgetPasswordPage)
            }
        ) {
            Text(
                stringResource(R.string.forget_password),
                style = TextStyle(
                    fontFamily = almarai_regular ,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        Spacer(Modifier.height(10.dp))

        //Sign In Button
        ButtonFill(
            onClick = {
            },
            text = stringResource(R.string.signIn)
        )


        //Sign Up Button
        ButtonWithBorder(
            onClick = {
                authNavController.navigate(RoutesAuth.signUpPage)
            },
            text = stringResource(R.string.signup)
        )



    }
}
package com.example.myapplication.presentation.screens.authScreen.forgetPasswordPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonWithBorder
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.InputsComponents.InputEmail
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.constant.RoutesAuth

@Composable
fun ForgetPasswordPage(authNavController: NavController , appNavController:NavController){
    val stateScroll = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(stateScroll),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){

        HeaderText(stringResource(R.string.forget_password), textAlign = TextAlign.Start)

        Spacer(Modifier.height(20.dp))

        ParagraphText(stringResource(R.string.pagrapgh_forget_password), textAlign = TextAlign.Start)

        Spacer(Modifier.height(10.dp))

        //Input Email
        InputEmail(
            getEmail = { email ->

            }
        )

        Spacer(Modifier.height(30.dp))

        //Button Send OTP
        ButtonFill(
            onClick = {
                val email = "test@email.com" // أو من TextField
                authNavController.navigate(RoutesAuth.verifyOTPWithEmail(email))

            },
            text = stringResource(R.string.send_otp)
        )

        //Button Back To Sign In
        ButtonWithBorder(
            onClick = {
                authNavController.popBackStack()
            },
            text = stringResource(R.string.back_to_sign_in)
        )

    }
}
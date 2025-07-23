package com.example.myapplication.presentation.screens.authScreen.newPasswordPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.myapplication.presentation.components.InputsComponents.InputPassword
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.constant.RoutesAuth


@Composable
fun NewForgetPasswordPage(authNavController: NavController , appNavController:NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        HeaderText(stringResource(R.string.header_new_password), textAlign = TextAlign.Start)

        Spacer(Modifier.height(20.dp))

        ParagraphText(stringResource(R.string.pargraph_new_password), textAlign = TextAlign.Start)

        Spacer(Modifier.height(20.dp))

        //Input NewPassword
        InputPassword(
            {password ->

            },
            label = stringResource(R.string.new_password),
            isNext = true
        )

        //Input Re Password
        InputPassword(
            {password ->

            },
            label = stringResource(R.string.re_new_password)
        )

        Spacer(Modifier.height(20.dp))

        //Button Change Password
        ButtonFill(
            onClick = {
            },
            text = stringResource(R.string.change_password)
        )

        //Button Back
        ButtonWithBorder(
            onClick = {
                authNavController.popBackStack()
            },
            text = stringResource(R.string.back)
        )
    }
}
package com.example.myapplication.presentation.screens.authScreen.verfiyOtp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonWithBorder
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.InputsComponents.InputNumber
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.constant.RoutesAuth
import com.example.myapplication.presentation.viewmodel.VerifyOTPViewModel
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.gray

@Composable
fun VerifyOTPPage(email: String, authNavController: NavController, verifyOTPViewModel: VerifyOTPViewModel = hiltViewModel()) {
    val stateScroll = rememberScrollState()
    val counterVerify = verifyOTPViewModel.counterOTP.collectAsState(initial = 60).value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(stateScroll),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderText(stringResource(R.string.header_verify_otp), textAlign = TextAlign.Start)

        Spacer(Modifier.height(20.dp))

        ParagraphText(stringResource(R.string.paragraph_verify_otp) + email, textAlign = TextAlign.Start)

        Spacer(Modifier.height(10.dp))

        //Input OTP Code
        InputNumber(
            getNumber = { otp ->

            },
            label = stringResource(R.string.otp_code),
        )

        //Button Resend Code OTP
        TextButton(
            onClick = {
                if (counterVerify == 0) {
                    verifyOTPViewModel.onResendCode()
                }
            },
            enabled = counterVerify == 0
        ) {
            Text(
                text = if (counterVerify > 0)
                    stringResource(R.string.message_timer, counterVerify)
                else
                    stringResource(R.string.resend_code),
                style = TextStyle(
                    fontSize = 14.sp ,
                    fontFamily = almarai_regular ,
                    color = if (counterVerify > 0) gray else blue,
                    textDecoration = if (counterVerify > 0) TextDecoration.None else TextDecoration.Underline,
                )
            )
        }

        Spacer(Modifier.height(10.dp))

        //Button Verify OTP Code
        ButtonFill(
            onClick = {
                authNavController.navigate(RoutesAuth.newPasswordPage)
            },
            text = stringResource(R.string.verify_code)
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
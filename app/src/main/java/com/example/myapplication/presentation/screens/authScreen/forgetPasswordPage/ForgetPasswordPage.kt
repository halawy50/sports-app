package com.example.myapplication.presentation.screens.authScreen.forgetPasswordPage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonWithBorder
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.InputsComponents.InputEmail
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.components.SnackBar
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.presentation.constant.routes.RoutesAuth
import com.example.myapplication.utils.validate.validateSendOTPInputs
import com.example.myapplication.presentation.viewmodel.SendOTPViewModel
import com.example.myapplication.utils.StateSendOTP

@Composable
fun ForgetPasswordPage(
    authNavController: NavController,
    appNavController:NavController,
    sendOTPViewModel: SendOTPViewModel= hiltViewModel()
){
    val scrollState = rememberScrollState()
    val stateSignUp by sendOTPViewModel.state.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Input states
    var mutableEmail by remember { mutableStateOf("") }

    // Error states - Initialize with default WrongVerify()
    var isWrongEmail by remember { mutableStateOf(WrongVerify()) }

    var isProgress by remember { mutableStateOf(false) }
    BackHandler(enabled = isProgress) {
        authNavController.navigate(RoutesAuth.loginPage) {
            popUpTo(0) { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ){

            HeaderText(stringResource(R.string.forget_password), textAlign = TextAlign.Start)

            Spacer(Modifier.height(20.dp))

            ParagraphText(stringResource(R.string.pagrapgh_forget_password), textAlign = TextAlign.Start)

            Spacer(Modifier.height(10.dp))

            // Input Email
            InputEmail(
                getEmail = { email ->
                    mutableEmail = email
                    // Clear error when user starts typing
                    if (isWrongEmail.isWrong) {
                        isWrongEmail = WrongVerify()
                    }
                },
                wrong = isWrongEmail
            )

            Spacer(Modifier.height(30.dp))

            //Button Send OTP
            ButtonFill(
                onClick = {
                    val request = validateSendOTPInputs(
                        email = mutableEmail,
                        setEmailError = { isWrongEmail = it },
                        context = context
                    )
                    request?.let {
                        sendOTPViewModel.sendOTP(it)
                    }
                },
                label = stringResource(R.string.send_otp)
            )

            //Button Back To Sign In
            ButtonWithBorder(
                onClick = {
                    authNavController.navigate(RoutesAuth.loginPage) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                text = stringResource(R.string.back_to_sign_in)
            )

        }

        //State Register
        when (val state = stateSignUp) {

            //Loading
            is StateSendOTP.Loading -> {
                LoadingDialog(stringResource(R.string.sending_otp))
                isProgress = true
            }

            //Success SendOTP
            is StateSendOTP.Success -> {
                LoadingDialog(stringResource(R.string.sending_otp))
                LaunchedEffect(Unit) {

                    snackbarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )

                    authNavController.navigate(RoutesAuth.verifyOTPWithEmail(mutableEmail))
                    sendOTPViewModel.resetState()
                    isProgress = false

                }
            }

            //Failure Register
            is StateSendOTP.Failure -> {
                LaunchedEffect(state) {
                    snackbarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )
                    sendOTPViewModel.resetState()
                    isProgress = false

                }
            }

            else -> {}
        }

        SnackBar(snackBarHostState = snackbarHostState , modifier = Modifier.align(Alignment.BottomCenter))
    }

}
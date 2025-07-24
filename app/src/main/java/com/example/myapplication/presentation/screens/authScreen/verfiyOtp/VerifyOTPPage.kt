package com.example.myapplication.presentation.screens.authScreen.verfiyOtp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonWithBorder
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.InputsComponents.InputNumber
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.components.SnackBar
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.presentation.constant.routes.RoutesAuth
import com.example.myapplication.utils.validate.validateVerifyOTPInputs
import com.example.myapplication.presentation.viewmodel.VerifyOTPViewModel
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.utils.StateVerify

@Composable
fun VerifyOTPPage(
    email: String,
    authNavController: NavController,
    verifyOTPViewModel: VerifyOTPViewModel = hiltViewModel()
) {
    val stateScroll = rememberScrollState()
    val counterVerify = verifyOTPViewModel.counterOTP.collectAsState(initial = 60).value
    val stateVerify by verifyOTPViewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Input states
    var mutableVerifyCode by remember { mutableStateOf("") }

    // Error states - Initialize with default WrongVerify()
    var isWrongVerifyCode by remember { mutableStateOf(WrongVerify()) }

    var isProgress by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize().imePadding()
    ){
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
                    mutableVerifyCode = otp
                    // Clear error when user starts typing
                    if (isWrongVerifyCode.isWrong) {
                        isWrongVerifyCode = WrongVerify()
                    }
                },
                wrong = isWrongVerifyCode,
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
                    val request = validateVerifyOTPInputs(
                        email = email,
                        verifyOTP = mutableVerifyCode,
                        setVerifyOTPError = { isWrongVerifyCode = it },
                        context= context
                    )

                    request?.let {
                        verifyOTPViewModel.verifyOTP(verifyCodeRequest = it)
                    }
                },
                label = stringResource(R.string.verify_code)
            )

            //Button Back
            ButtonWithBorder(
                onClick = {
                    authNavController.popBackStack()
                },
                text = stringResource(R.string.back)
            )

        }

        //State Login
        when (val state = stateVerify) {

            //Loading
            is StateVerify.Loading -> {
                LoadingDialog(stringResource(id = R.string.loading_verify_code))
            }

            //Success Verify
            is StateVerify.Success -> {
                LoadingDialog(stringResource(id = R.string.otp_verified_success))

                LaunchedEffect(Unit) {
                    snackBarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )
                    authNavController.navigate(RoutesAuth.newPasswordPage(
                        email = email,
                        otp = mutableVerifyCode
                    )){
                        verifyOTPViewModel.resetState()
                    }

                }
            }

            //Failure Verify
            is StateVerify.Failure -> {

                LaunchedEffect(state) {

                    snackBarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )
                    verifyOTPViewModel.resetState()
                }
            }

            else -> {}
        }

        SnackBar(snackBarHostState = snackBarHostState , modifier = Modifier.align(Alignment.BottomCenter))

    }
}
package com.example.myapplication.presentation.screens.authScreen.newPasswordPage

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
import com.example.myapplication.presentation.components.InputsComponents.InputPassword
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.components.SnackBar
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.presentation.constant.routes.RoutesAuth
import com.example.myapplication.utils.validate.validateResetPasswordInputs
import com.example.myapplication.presentation.viewmodel.ResetPasswordViewModel
import com.example.myapplication.utils.StateResetPassword


@Composable
fun ResetPasswordPage(
    email: String,
    otp: String,
    authNavController: NavController,
    appNavController:NavController,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
){
    val stateScroll = rememberScrollState()
    val stateResetPassword by resetPasswordViewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Input states
    var mutablePassword by remember { mutableStateOf("") }
    var mutableRePassword by remember { mutableStateOf("") }

    // Error states - Initialize with default WrongVerify()
    var isWrongPassword by remember { mutableStateOf(WrongVerify()) }
    var isWrongRePassword by remember { mutableStateOf(WrongVerify()) }

    var isProgress by remember { mutableStateOf(false) }

    BackHandler(enabled = isProgress) {
        authNavController.navigate(RoutesAuth.forgetPasswordPage) {
            popUpTo(0) { inclusive = true }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(stateScroll),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            HeaderText(stringResource(R.string.header_new_password), textAlign = TextAlign.Start)

            Spacer(Modifier.height(20.dp))

            ParagraphText(stringResource(R.string.pargraph_new_password), textAlign = TextAlign.Start)

            Spacer(Modifier.height(20.dp))

            //Input NewPassword
            InputPassword(
                getPassword = { password->
                    mutablePassword = password
                    // Clear error when user starts typing
                    if (isWrongPassword.isWrong) {
                        isWrongPassword = WrongVerify()
                    }
                },
                wrong = isWrongPassword,
                label = stringResource(R.string.new_password),
                isNext = true
            )

            //Input Re Password
            InputPassword(
                getPassword = { rePassword->
                    mutableRePassword = rePassword

                    if (isWrongRePassword.isWrong) {
                        isWrongRePassword = WrongVerify()
                    }
                },
                wrong = isWrongRePassword,
                label = stringResource(R.string.re_new_password)
            )

            Spacer(Modifier.height(20.dp))

            //Button Change Password
            ButtonFill(
                onClick = {
                    val request = validateResetPasswordInputs(
                        email = email,
                        password = mutablePassword,
                        rePassword = mutableRePassword,
                        verifyOTP = otp,
                        setPasswordError = { isWrongPassword = it },
                        setRePasswordError = { isWrongRePassword = it },
                        context = context
                    )
                    request?.let {
                        resetPasswordViewModel.resetPassword(newPasswordAndOtpRequest = it)
                    }
                },
                label = stringResource(R.string.change_password)
            )

            //Button Back
            ButtonWithBorder(
                onClick = {
                    authNavController.navigate(RoutesAuth.forgetPasswordPage){
                        popUpTo(0){inclusive = true}
                    }
                },
                text = stringResource(R.string.back)
            )
        }

        //State ResetPassword
        when (val state = stateResetPassword) {

            //Loading
            is StateResetPassword.Loading -> {
                LoadingDialog(stringResource(id = R.string.reset_password_loading))
                isProgress = true
            }

            //Success Verify
            is StateResetPassword.Success -> {
                LoadingDialog(stringResource(id = R.string.reset_password_success))
                LaunchedEffect(Unit) {
                    snackBarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )
                    authNavController.navigate(RoutesAuth.loginPage){
                        popUpTo(RoutesAuth.forgetPasswordPage){inclusive = true}
                        popUpTo(RoutesAuth.verifyOTPPage){inclusive = true}
                    }
                    resetPasswordViewModel.resetState()
                    isProgress = false

                }
            }

            //Failure Verify
            is StateResetPassword.Failure -> {

                LaunchedEffect(state) {

                    snackBarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )
                    resetPasswordViewModel.resetState()
                    isProgress = false

                }
            }

            else -> {}
        }

        SnackBar(snackBarHostState = snackBarHostState , modifier = Modifier.align(Alignment.BottomCenter))

    }
}
package com.example.myapplication.presentation.screens.authScreen.loginPage

import android.content.Context
import android.provider.Settings.Global.putString
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonWithBorder
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.components.InputsComponents.InputPassword
import com.example.myapplication.presentation.components.InputsComponents.InputEmail
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.presentation.components.SnackBar
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.presentation.constant.routes.RoutesAuth
import com.example.myapplication.utils.validate.validateLoginInputs
import com.example.myapplication.presentation.viewmodel.LoginViewModel
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.utils.StateLogin

@Composable
fun LoginPage(
    authNavController: NavController,
    appNavController:NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
){

    // Input states
    var mutableEmail by remember { mutableStateOf("") }
    var mutablePassword by remember { mutableStateOf("") }

    // Error states - Initialize with default WrongVerify()
    var isWrongEmail by remember { mutableStateOf(WrongVerify()) }
    var isWrongPassword by remember { mutableStateOf(WrongVerify()) }


    val stateLogin by loginViewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    var isProgress by remember { mutableStateOf(false) }
    BackHandler(enabled = isProgress) {

    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .imePadding(),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
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
                    mutableEmail = email
                    // Clear error when user starts typing
                    if (isWrongEmail.isWrong) {
                        isWrongEmail = WrongVerify()
                    }
                },
                wrong = isWrongEmail

            )


            //Input Password
            InputPassword(
                getPassword = { password->
                    mutablePassword = password
                    // Clear error when user starts typing
                    if (isWrongPassword.isWrong) {
                        isWrongPassword = WrongVerify()
                    }
                },
                wrong = isWrongPassword
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
                    val request = validateLoginInputs(
                        email = mutableEmail,
                        password = mutablePassword,
                        setEmailError = { isWrongEmail = it },
                        setPasswordError = { isWrongPassword = it },
                        context
                    )

                    request?.let {

                        loginViewModel.login(loginRequest = it)
                    }
                },
                label = stringResource(R.string.signIn)
            )


            //Sign Up Button
            ButtonWithBorder(
                onClick = {
                    authNavController.navigate(RoutesAuth.signUpPage)
                },
                text = stringResource(R.string.signup)
            )



        }

        //State Login
        when (val state = stateLogin) {

            //Loading
            is StateLogin.Loading -> {
                isProgress = true

                LoadingDialog(stringResource(R.string.loading_login))

            }

            //Success Register
            is StateLogin.Success -> {

                LoadingDialog(stringResource(id = R.string.login_success_message))

                LaunchedEffect(Unit) {
                    snackBarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )
                    val prefs = context.getSharedPreferences("token", Context.MODE_PRIVATE)

                    prefs.edit {
                        putString("access_token", state.data.accessToken)
                            .putString("refresh_token", state.data.refreshToken)
                    }

                    appNavController.navigate(Routes.mainScreen){
                        popUpTo(0){inclusive = true}
                    }
                    loginViewModel.resetState()
                    isProgress = false

                }
            }

            //Success Failure Register
            is StateLogin.Failure -> {

                LaunchedEffect(state) {

                    snackBarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )
                    loginViewModel.resetState()
                    isProgress = false

                }
            }

            else -> {}
        }

        SnackBar(snackBarHostState = snackBarHostState , modifier = Modifier.align(Alignment.BottomCenter))
    }

}
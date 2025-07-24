package com.example.myapplication.presentation.screens.authScreen.signUpPage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonWithBorder
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.InputsComponents.DropDawnSelect
import com.example.myapplication.presentation.components.InputsComponents.InputEmail
import com.example.myapplication.presentation.components.InputsComponents.InputNumber
import com.example.myapplication.presentation.components.InputsComponents.InputPassword
import com.example.myapplication.presentation.components.InputsComponents.InputText
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.components.SnackBar
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.presentation.constant.routes.RoutesAuth
import com.example.myapplication.presentation.constant.cityList
import com.example.myapplication.presentation.constant.genderList
import com.example.myapplication.utils.validate.validateSignUpInputs
import com.example.myapplication.presentation.viewmodel.RegisterViewModel
import com.example.myapplication.utils.StateRegister

@Composable
fun SignUpPage(
    authNavController: NavController,
    appNavController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val genderList = genderList(context)
    val cityList = cityList(context)
    val scrollState = rememberScrollState()
    val stateSignUp by registerViewModel.state.collectAsState()
    // Input states
    var mutableFullName by remember { mutableStateOf("") }
    var mutableAge by remember { mutableIntStateOf(0) }
    var mutableGender by remember { mutableStateOf(Gender(index = 0, gender = "Male")) }
    var mutableCity by remember { mutableStateOf(City(index = 0, city = "Cairo")) }
    var mutableEmail by remember { mutableStateOf("") }
    var mutablePassword by remember { mutableStateOf("") }

    // Error states - Initialize with default WrongVerify()
    var isWrongFullName by remember { mutableStateOf(WrongVerify()) }
    var isWrongAge by remember { mutableStateOf(WrongVerify()) }
    var isWrongGender by remember { mutableStateOf(WrongVerify()) }
    var isWrongCity by remember { mutableStateOf(WrongVerify()) }
    var isWrongEmail by remember { mutableStateOf(WrongVerify()) }
    var isWrongPassword by remember { mutableStateOf(WrongVerify()) }


    val snackbarHostState = remember { SnackbarHostState() }


    var isProgress by remember { mutableStateOf(false) }
    BackHandler(enabled = isProgress) {

    }
    Box(modifier = Modifier.fillMaxSize()){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState).padding()
                .imePadding(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.height(50.dp))

            HeaderText(stringResource(R.string.header_sign_up), textAlign = TextAlign.Start)

            Spacer(Modifier.height(20.dp))

            ParagraphText(stringResource(R.string.paragraph_signup), textAlign = TextAlign.Start)

            Spacer(Modifier.height(30.dp))

            // Input Name
            InputText(
                getText = { name ->
                    mutableFullName = name
                    // Clear error when user starts typing
                    if (isWrongFullName.isWrong) {
                        isWrongFullName = WrongVerify()
                    }
                },
                label = stringResource(R.string.full_name),
                wrong = isWrongFullName
            )

            Spacer(Modifier.height(10.dp))

            // DropDown Select Gender
            DropDawnSelect(
                list = genderList,
                getSelected = { gender ->
                    mutableGender = Gender(index = gender.index, gender = gender.title)
                    // Clear error when user selects
                    if (isWrongGender.isWrong) {
                        isWrongGender = WrongVerify()
                    }
                },
                label = stringResource(R.string.gender)
            )

            Spacer(Modifier.height(10.dp))

            // DropDown Select Cities
            DropDawnSelect(
                list = cityList,
                getSelected = { city ->
                    mutableCity = City(index = city.index, city = city.title)
                    // Clear error when user selects
                    if (isWrongCity.isWrong) {
                        isWrongCity = WrongVerify()
                    }
                },
                label = stringResource(R.string.city)
            )

            Spacer(Modifier.height(10.dp))

            // Input Age
            InputNumber(
                getNumber = { age ->
                    mutableAge = age.toIntOrNull() ?: 0
                    // Clear error when user starts typing
                    if (isWrongAge.isWrong) {
                        isWrongAge = WrongVerify()
                    }
                },
                label = stringResource(R.string.age),
                wrong = isWrongAge
            )

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

            Spacer(Modifier.height(10.dp))

            // Input Password
            InputPassword(
                getPassword = { password ->
                    mutablePassword = password
                    // Clear error when user starts typing
                    if (isWrongPassword.isWrong) {
                        isWrongPassword = WrongVerify()
                    }
                },
                wrong = isWrongPassword
            )

            Spacer(Modifier.height(20.dp))

            // Button Sign Up
            ButtonFill(
                onClick = {
                    val request = validateSignUpInputs(
                        fullName = mutableFullName,
                        age = mutableAge,
                        gender = mutableGender,
                        city = mutableCity,
                        email = mutableEmail,
                        password = mutablePassword,
                        setFullNameError = { isWrongFullName = it },
                        setAgeError = { isWrongAge = it },
                        setGenderError = { isWrongGender = it },
                        setCityError = { isWrongCity = it },
                        setEmailError = { isWrongEmail = it },
                        setPasswordError = { isWrongPassword = it },
                        context
                    )

                    request?.let {
                        registerViewModel.register(it)
                    }
                },
                label = stringResource(R.string.signup)
            )


            // Button Back To Sign In
            ButtonWithBorder(
                onClick = {
                    authNavController.popBackStack()
                },
                text = stringResource(R.string.already_have_account)
            )

            Spacer(Modifier.height(20.dp))
        }

        //State Register
        when (val state = stateSignUp) {


            //Loading
            is StateRegister.Loading -> {
                isProgress = true
                LoadingDialog(stringResource(R.string.loading_creating_account))
            }

            //Success Register
            is StateRegister.Success -> {
                LoadingDialog(stringResource(R.string.account_created_success))
                LaunchedEffect(Unit) {

                    snackbarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )

                    authNavController.navigate(RoutesAuth.loginPage){
                        popUpTo(RoutesAuth.signUpPage){inclusive = true}
                        registerViewModel.resetState()
                    }


                }
                isProgress = false

            }

            //Failure Register
            is StateRegister.Failure -> {

                LaunchedEffect(state) {
                    snackbarHostState.showSnackbar(
                        if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                        else state.data.messageEn
                    )
                    registerViewModel.resetState()
                }
                isProgress = false

            }

            else -> {}
        }

        SnackBar(snackBarHostState = snackbarHostState , modifier = Modifier.align(Alignment.BottomCenter))


    }

}


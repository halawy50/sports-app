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
import com.example.myapplication.domain.model.EntryModel
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.Governorate
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
import com.example.myapplication.presentation.constant.genderList
import com.example.myapplication.presentation.viewmodel.CityAndGovernorateViewModel
import com.example.myapplication.utils.validate.validateSignUpInputs
import com.example.myapplication.presentation.viewmodel.RegisterViewModel
import com.example.myapplication.utils.StateCities
import com.example.myapplication.utils.StateGovernorate
import com.example.myapplication.utils.StateRegister

@Composable
fun SignUpPage(
    authNavController: NavController,
    appNavController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel(),
    cityAndGovernorateViewModel: CityAndGovernorateViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val genderList = genderList()
    val governorateList by cityAndGovernorateViewModel.governorate.collectAsState()
    val governorateState by cityAndGovernorateViewModel.stateGovernorate.collectAsState()
    val cityList by cityAndGovernorateViewModel.cities.collectAsState()
    val cityState by cityAndGovernorateViewModel.stateCities.collectAsState()

    val scrollState = rememberScrollState()
    val stateSignUp by registerViewModel.state.collectAsState()
    // Input states
    var mutableFullName by remember { mutableStateOf("") }
    var mutableAge by remember { mutableIntStateOf(0) }
    var mutableGender by remember { mutableStateOf(Gender(index = genderList()[0].index, genderEn = genderList()[0].titleEn, genderAr = genderList()[0].titleEn)) }
    var mutableGovernorate by remember { mutableStateOf(Governorate(id = "-1", governorateNameAr = "" , governorateNameEn = "")) }
    var mutableCity by remember { mutableStateOf(City(id = "-1", city_name_ar = "", city_name_en = "", governorate_id = "")) }
    var mutableEmail by remember { mutableStateOf("") }
    var mutablePassword by remember { mutableStateOf("") }
    var cityListDropDawn by remember { mutableStateOf<List<EntryModel>>(emptyList()) }

    // Error states - Initialize with default WrongVerify()
    var isWrongFullName by remember { mutableStateOf(WrongVerify()) }
    var isWrongAge by remember { mutableStateOf(WrongVerify()) }
    var isWrongGender by remember { mutableStateOf(WrongVerify()) }
    var isWrongGovernorate by remember { mutableStateOf(WrongVerify()) }
    var isWrongCity by remember { mutableStateOf(WrongVerify()) }
    var isWrongEmail by remember { mutableStateOf(WrongVerify()) }
    var isWrongPassword by remember { mutableStateOf(WrongVerify()) }


    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(cityList) {
        if (!cityList.isNullOrEmpty()) {
            cityListDropDawn = cityList.mapNotNull {
                EntryModel(
                    index = it.id.toIntOrNull() ?: -1,
                    titleAr = it.city_name_ar,
                    titleEn = it.city_name_en
                )
            }

            val first = cityListDropDawn.first()
            mutableCity = City(
                id = first.index.toString(),
                city_name_ar = first.titleAr,
                city_name_en = first.titleEn,
                governorate_id = mutableGovernorate.id
            )
        } else {
            cityListDropDawn = listOf(
                EntryModel(index = -1, titleAr = "لا توجد مدن", titleEn = "No Cities Available")
            )
        }
    }



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
                    mutableGender = Gender(index = gender.index, genderAr = gender.titleAr, genderEn = gender.titleEn)
                    // Clear error when user selects
                    if (isWrongGender.isWrong) {
                        isWrongGender = WrongVerify()
                    }
                },
                label = stringResource(R.string.gender),
                wrong = isWrongGender

            )

            Spacer(Modifier.height(10.dp))

//         if (!governorateList.isNullOrEmpty())
            // DropDown Select Governorate
            DropDawnSelect(
                list = if (!governorateList.isNullOrEmpty()){
                    governorateList.mapIndexed { index, item ->
                        EntryModel(index = item.id.toInt(), titleAr = item.governorateNameAr, titleEn = item.governorateNameEn)
                    }
                }else listOf(EntryModel(index = -1 , titleEn = "", titleAr = "")) ,
                getSelected = { governorate ->

                    mutableGovernorate = Governorate(id = governorate.index.toString(), governorateNameAr = governorate.titleAr, governorateNameEn = governorate.titleEn)

                    if(mutableGovernorate.id.toInt()>0){
                        cityAndGovernorateViewModel.setCity(emptyList())
                        cityAndGovernorateViewModel.getCity(governorateId = mutableGovernorate.id)

                    }


                    // Clear error when user selects
                    if (isWrongGovernorate.isWrong) {
                        isWrongGovernorate = WrongVerify()
                    }
                },
                label = stringResource(R.string.governorate),
                wrong = isWrongGovernorate
            )

            Spacer(Modifier.height(10.dp))


            if (cityListDropDawn.isNotEmpty() && cityListDropDawn.first().index != -1)
                // DropDown Select Cities
                DropDawnSelect(
                    list =  cityListDropDawn,
                    getSelected = { city ->
                        mutableCity = City(
                            id = city.index.toString(),
                            city_name_en = city.titleEn,
                            city_name_ar = city.titleAr,
                            governorate_id = mutableGovernorate.id
                            )
                        // Clear error when user selects
                        if (isWrongCity.isWrong) {
                            isWrongCity = WrongVerify()
                        }
                    },
                    label = stringResource(id = R.string.city_or_center),
                    wrong = isWrongCity

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
                        governorate = mutableGovernorate,
                        city = mutableCity,
                        email = mutableEmail,
                        password = mutablePassword,
                        setFullNameError = { isWrongFullName = it },
                        setAgeError = { isWrongAge = it },
                        setGenderError = { isWrongGender = it },
                        setGovernorateError = { isWrongGovernorate = it },
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


        when(val state = governorateState){
            is StateGovernorate.Idle ->{}
            is StateGovernorate.Loading -> {
                LoadingDialog()
            }
            is StateGovernorate.Success ->{
                cityAndGovernorateViewModel.setGovernorate(state.data)
            }
            is StateGovernorate.Failure ->{
                cityAndGovernorateViewModel.setGovernorate(state.data)
            }
            else -> {}
        }

        when(val state = cityState){
            is StateCities.Idle ->{}
            is StateCities.Loading -> {
                LoadingDialog()
            }
            is StateCities.Success ->{
                cityAndGovernorateViewModel.setCity(state.data)
            }
            is StateCities.Failure ->{
                cityAndGovernorateViewModel.setCity(state.data)
                val message = stringResource(id = R.string.check_internet)
                LaunchedEffect(state) {
                    snackbarHostState.showSnackbar(
                        message = message
                    )
                }

            }
            else -> {}
        }

    }

}


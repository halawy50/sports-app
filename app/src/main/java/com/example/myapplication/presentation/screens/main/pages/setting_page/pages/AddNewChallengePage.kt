package com.example.myapplication.presentation.screens.main.pages.setting_page.pages

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.EntryModel
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.Governorate
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.presentation.components.AlertDialog
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.InputsComponents.DropDawnSelect
import com.example.myapplication.presentation.components.InputsComponents.InputText
import com.example.myapplication.presentation.components.InputsComponents.LongText
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.presentation.components.SnackBar
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.presentation.constant.challengeTeamList
import com.example.myapplication.presentation.constant.genderList
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.presentation.constant.teamList
import com.example.myapplication.presentation.viewmodel.AddNewChallengeViewModel
import com.example.myapplication.presentation.viewmodel.CityAndGovernorateViewModel
import com.example.myapplication.utils.StateAddNewChallenge
import com.example.myapplication.utils.StateCities
import com.example.myapplication.utils.StateGovernorate
import com.example.myapplication.utils.StateLogin
import com.example.myapplication.utils.validate.validateAddChallenge
import com.example.myapplication.utils.validate.validateSignUpInputs

@Composable
fun AddNewChallenges(
    appNavController: NavController,
    cityAndGovernorateViewModel: CityAndGovernorateViewModel = hiltViewModel(),
    addNewChallengeViewModel: AddNewChallengeViewModel = hiltViewModel()
){

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val genderList = teamList()
    val challengerTeamList = challengeTeamList()
    val governorateList by cityAndGovernorateViewModel.governorate.collectAsState()
    val governorateState by cityAndGovernorateViewModel.stateGovernorate.collectAsState()
    val cityList by cityAndGovernorateViewModel.cities.collectAsState()
    val cityState by cityAndGovernorateViewModel.stateCities.collectAsState()
    val stateAddNewChallenge by addNewChallengeViewModel.stateAddNewChallenge.collectAsState()

    // Input states
    var mutableDescribe by remember { mutableStateOf("") }
    var mutableGender by remember { mutableStateOf(Gender(index = genderList()[0].index, genderEn = genderList()[0].titleEn, genderAr = genderList()[0].titleEn)) }
    var mutableChallengeTeam by remember { mutableStateOf(1) }
    var mutableGovernorate by remember { mutableStateOf(Governorate(id = "-1", governorateNameAr = "" , governorateNameEn = "")) }
    var mutableCity by remember { mutableStateOf(City(id = "-1", city_name_ar = "", city_name_en = "", governorate_id = "")) }
    var cityListDropDawn by remember { mutableStateOf<List<EntryModel>>(emptyList()) }
    var mutableClub by remember { mutableStateOf("") }


    // Error states - Initialize with default WrongVerify()
    var isWrongDescribe by remember { mutableStateOf(WrongVerify()) }
    var isWrongGender by remember { mutableStateOf(WrongVerify()) }
    var isWrongGovernorate by remember { mutableStateOf(WrongVerify()) }
    var isWrongCity by remember { mutableStateOf(WrongVerify()) }
    var isWrongClub by remember { mutableStateOf(WrongVerify()) }
    var isWrongChallengeTeam by remember { mutableStateOf(WrongVerify()) }

    val snackbarHostState = remember { SnackbarHostState() }
    var isProgress by remember { mutableStateOf(false) }


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


    Scaffold { innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 10.dp)
                    .verticalScroll(scrollState)
                    .padding()
                    .imePadding()
            )
            {


                Spacer(Modifier.height(20.dp))

                //Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    HeaderText(text = stringResource(R.string.add_new_challenge))

                    IconButton(onClick = {
//                    navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.graphicsLayer(rotationZ = 180f)

                        )
                    }

                } //end Header

                Column(
                    modifier = Modifier.padding(end = 10.dp)
                ) {

                    Spacer(Modifier.height(20.dp))

                    // Input Describe
                    LongText(
                        getText = { name ->
                            mutableDescribe = name
                            // Clear error when user starts typing
                            if (isWrongDescribe.isWrong) {
                                isWrongDescribe = WrongVerify()
                            }
                        },
                        label = stringResource(R.string.describe),
                        wrong = isWrongDescribe
                    )

                    Spacer(Modifier.height(10.dp))

                    // Input Club
                    InputText(
                        getText = { name ->
                            mutableClub = name
                            // Clear error when user starts typing
                            if (isWrongClub.isWrong) {
                                isWrongClub = WrongVerify()
                            }
                        },
                        label = stringResource(R.string.club),
                        wrong = isWrongClub
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
                        label = stringResource(R.string.team),
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
                                cityAndGovernorateViewModel.getCity(governorate = mutableGovernorate)



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

                    // DropDown Select Challenge Team
                    DropDawnSelect(
                        list = challengerTeamList,
                        getSelected = { challengerTeam ->
                            mutableChallengeTeam = challengerTeam.index
                            // Clear error when user selects
                            if (isWrongChallengeTeam.isWrong) {
                                isWrongChallengeTeam = WrongVerify()
                            }
                        },
                        label = stringResource(R.string.challange_team),
                        wrong = isWrongGender

                    )

                    Spacer(Modifier.height(20.dp))

                    // Button Sign Up
                    ButtonFill(
                        onClick = {
                            val request = validateAddChallenge(
                                description = mutableDescribe,
                                club = mutableClub,
                                teamChallenger = mutableChallengeTeam,
                                gender = mutableGender,
                                governorate = mutableGovernorate,
                                city = mutableCity,

                                setGenderError = { isWrongGender = it },
                                setGovernorateError = { isWrongGovernorate = it },
                                setCityError = { isWrongCity = it },
                                setTeamChallengeError = { isWrongChallengeTeam = it },
                                context
                            )


                            request?.let {
                                addNewChallengeViewModel.addNewChallenge(it)
                            }
                        },
                        label = stringResource(R.string.add_new_challenge)
                    )
                }

            }


            //State Add New Challenge
            when (val state = stateAddNewChallenge) {

                //Loading
                is StateAddNewChallenge.Loading -> {
                    isProgress = true

                    LoadingDialog(message = stringResource(R.string.adding_challenge))

                }

                //Success Added Challenge
                is StateAddNewChallenge.Success -> {

                    LoadingDialog(message = stringResource(id = R.string.challenge_added_successfully))

                    LaunchedEffect(Unit) {
                        snackbarHostState.showSnackbar(
                            if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                            else state.data.messageEn
                        )


                        appNavController.navigate(Routes.mainScreen){
                            popUpTo(0){inclusive = true}
                        }

                        isProgress = false

                    }
                }

                //UnAuthorization
                is StateAddNewChallenge.UnAuthorization -> {
                    AlertDialog(
                        messageAlert = stringResource(R.string.session_expired_message),
                        titleButtonOne = stringResource(R.string.back_to_login_button),
                        isButtonOne = true,
                        onClickButtonOne = {

                            addNewChallengeViewModel.resetState()

                            appNavController.navigate(Routes.authScreen){
                                popUpTo(0){inclusive = true}
                            }
                        },

                    )
                }


                // Failure Added Challenge
                is StateAddNewChallenge.Failure -> {

                    LaunchedEffect(state) {

                        snackbarHostState.showSnackbar(
                            if (ChangeLanguage.getSavedLanguage(context)=="ar") state.data.messageAr
                            else state.data.messageEn
                        )
                        isProgress = false

                    }
                }

                else -> {}
            }



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

            SnackBar(snackBarHostState = snackbarHostState , modifier = Modifier.align(Alignment.BottomCenter))

        }


    }
}
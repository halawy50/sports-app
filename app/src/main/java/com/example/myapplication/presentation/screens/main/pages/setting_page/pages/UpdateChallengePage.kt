import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.viewmodel.CityAndGovernorateViewModel
import com.example.myapplication.presentation.viewmodel.HomeChallengesViewModel
import com.example.myapplication.presentation.viewmodel.UpdateChallengeViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.EntryModel
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.Governorate
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.presentation.components.AlertDialog
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.HeaderTopBar
import com.example.myapplication.presentation.components.InputsComponents.DropDawnSelect
import com.example.myapplication.presentation.components.InputsComponents.InputNumber
import com.example.myapplication.presentation.components.InputsComponents.InputText
import com.example.myapplication.presentation.components.InputsComponents.LongText
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.presentation.components.SnackBar
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.presentation.constant.challengeTeamList
import com.example.myapplication.presentation.constant.genderList
import com.example.myapplication.presentation.constant.genderTeamList
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.utils.GlobalState
import com.example.myapplication.utils.StateCities
import com.example.myapplication.utils.StateGovernorate
import com.example.myapplication.utils.StateUpdateChallenge
import com.example.myapplication.utils.validate.validateAddChallenge


@Composable
fun UpdateChallengePage(
    updateChallengeViewModel: UpdateChallengeViewModel = hiltViewModel(),
    cityAndGovernorateViewModel: CityAndGovernorateViewModel = hiltViewModel(),
    homeChallengesViewModel: HomeChallengesViewModel,
    appNavController: NavController,
    challengeID: String,
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val governorateState by cityAndGovernorateViewModel.stateGovernorate.collectAsState()
    val cityState by cityAndGovernorateViewModel.stateCities.collectAsState()
    val challenge by updateChallengeViewModel.challenge.collectAsState()
    val challengeState by updateChallengeViewModel.stateGetChallenge.collectAsState()
    val stateUpdateChallenge by updateChallengeViewModel.stateUpdateChallenge.collectAsState()

    var mutableListGovernorate by remember { mutableStateOf(emptyList<EntryModel>()) }
    var mutableListCity by remember { mutableStateOf(emptyList<EntryModel>()) }

    var userChangedCity by remember { mutableStateOf(false) }

    var defaultDescribe by remember { mutableStateOf("") }
    var defaultClub by remember { mutableStateOf("") }
    var defaultWhatsUp by remember { mutableStateOf("") }
    var defaultGovernorateSelect by remember {
        mutableStateOf(
            EntryModel(
                index = -1,
                titleAr = "",
                titleEn = ""
            )
        )
    }
    var defaultCitySelect by remember {
        mutableStateOf(
            EntryModel(
                index = -1,
                titleAr = "",
                titleEn = ""
            )
        )
    }
    var mutableGovernorate by remember {
        mutableStateOf(
            Governorate(
                id = "-1",
                governorateNameAr = "",
                governorateNameEn = ""
            )
        )
    }
    var isWrongGovernorate by remember { mutableStateOf(WrongVerify()) }
    var isBack by remember { mutableStateOf(false) }

    // Error states - Initialize with default WrongVerify()
    var isWrongDescribe by remember { mutableStateOf(WrongVerify()) }
    var isWrongGender by remember { mutableStateOf(WrongVerify()) }
    var isWrongCity by remember { mutableStateOf(WrongVerify()) }
    var isWrongClub by remember { mutableStateOf(WrongVerify()) }
    var isWrongChallengeTeam by remember { mutableStateOf(WrongVerify()) }
    var isWrongWhatsUp by remember { mutableStateOf(WrongVerify()) }
    val snackbarHostState = remember { SnackbarHostState() }
    var isProgress by remember { mutableStateOf(false) }

    // حالات اختيار فريق وجنس
    val genderList = genderTeamList()
    val teamList = challengeTeamList()

    var mutableGender by remember { mutableStateOf(genderList.first()) }
    var mutableTeam by remember { mutableStateOf(teamList.first()) }

    // Loading states
    var isLoading by remember { mutableStateOf(true) }
    var isLoadingCities by remember { mutableStateOf(false) }

    // جلب التحدي عند بدء الصفحة
    LaunchedEffect(Unit) {
        updateChallengeViewModel.getSingleChallenge(challengeID)
    }

    // تحديث المحافظات بعد جلب المحافظات والتحدي
    LaunchedEffect(governorateState, challengeState) {
        if (governorateState is StateGovernorate.Success && challengeState == GlobalState.SUCCESS) {
            val govList = (governorateState as StateGovernorate.Success).data
            val chal = challenge

            if (!govList.isNullOrEmpty() && chal != null) {
                val mappedList = govList.map {
                    EntryModel(
                        index = it.id.toInt(),
                        titleAr = it.governorateNameAr,
                        titleEn = it.governorateNameEn
                    )
                }
                val defaultGov = EntryModel(
                    index = chal.governorate.id.toInt(),
                    titleAr = chal.governorate.governorateNameAr,
                    titleEn = chal.governorate.governorateNameEn
                )

                mutableListGovernorate =
                    listOf(defaultGov) + mappedList.filter { it.index != defaultGov.index }
                defaultGovernorateSelect = defaultGov
                mutableGovernorate = chal.governorate

                isLoadingCities = true
                cityAndGovernorateViewModel.getCity(governorateId = chal.governorate.id)
            }
        }
    }

    // تحديث المدن والقيمة الافتراضية
    LaunchedEffect(cityState, challenge) {
        if (cityState is StateCities.Success && challenge != null) {
            val cities = (cityState as StateCities.Success).data
            if (!cities.isNullOrEmpty()) {
                val mappedCityList = cities.map {
                    EntryModel(
                        index = it.id.toInt(),
                        titleAr = it.city_name_ar,
                        titleEn = it.city_name_en
                    )
                }
                mutableListCity = mappedCityList

                if (!userChangedCity) {
                    val defaultCity =
                        mappedCityList.find { it.index == challenge!!.city.id.toInt() }
                    defaultCitySelect = defaultCity ?: mappedCityList.first()
                }
            } else {
                mutableListCity = emptyList()
                defaultCitySelect = EntryModel(index = -1, titleAr = "", titleEn = "")
            }
            isLoadingCities = false
        }
    }

    // تحديث isLoading حسب انتهاء التحميلات
    LaunchedEffect(isLoadingCities, challengeState, governorateState) {
        isLoading = when {
            governorateState !is StateGovernorate.Success -> true
            challengeState != GlobalState.SUCCESS -> true
            isLoadingCities -> true
            else -> false
        }
    }

    // تعيين القيمة الافتراضية للجنس والفريق عند جلب التحدي
    LaunchedEffect(challenge) {
        challenge?.let { chal ->
            Log.d("UpdateChallengePage", "Challenge gender index: ${chal.gender.index}")
            Log.d("UpdateChallengePage", "Challenge team value: ${chal.team}")
            Log.d("UpdateChallengePage", "Challenge Describe value: ${chal.description}")
            Log.d("UpdateChallengePage", "Challenge Club value: ${chal.club}")

            val genderFromChallenge = genderList.find { it.index == chal.gender.index }
            if (genderFromChallenge != null) {
                mutableGender = genderFromChallenge
            }

            val teamFromChallenge = teamList.find { it.index == chal.team - 1 }
            if (teamFromChallenge != null) {
                mutableTeam = teamFromChallenge
            }

            defaultDescribe = chal.description
            defaultClub = chal.club
            defaultWhatsUp = chal.whatsUpNumber
        }
    }

    Scaffold { innerPadding ->

        BackHandler {
            if (!isProgress || !isLoading) {
                isBack = true
            }
        }

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 10.dp)
                    .verticalScroll(scrollState)
                    .padding()
                    .imePadding()
            )
            {
                //Header
                HeaderTopBar(
                    onClick = {
                        isBack = true
                    },
                    title = stringResource(R.string.update_challenge)
                )//end Header

                Column(
                    modifier = Modifier.padding(end = 10.dp)
                ) {

                    // Input Describe
                    LongText(
                        initialValue = defaultDescribe,
                        getText = { name ->
                            defaultDescribe = name
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
                        initialValue = defaultClub,
                        getText = { name ->
                            defaultClub = name
                            // Clear error when user starts typing
                            if (isWrongClub.isWrong) {
                                isWrongClub = WrongVerify()
                            }
                        },
                        label = stringResource(R.string.club),
                        wrong = isWrongClub
                    )

                    Spacer(Modifier.height(10.dp))

                    //WhatsUp Number
                    InputNumber(
                        initialValue = defaultWhatsUp,
                        getNumber = { whatsUpNumber ->
                            defaultWhatsUp = whatsUpNumber
                        },
                        label = stringResource(id = R.string.label_whatsapp),
                        placeholder = stringResource(id = R.string.placeholder_whatsapp),
                        maxInputNumber = 11,
                        wrong = isWrongWhatsUp
                    )


                    Spacer(Modifier.height(10.dp))

                    // المحافظات
                    if (mutableListGovernorate.isNotEmpty()) {
                        DropDawnSelect(
                            list = mutableListGovernorate,
                            defaultSelected = defaultGovernorateSelect,
                            selectFirst = true,
                            getSelected = { governorate ->
                                val newGovernorate = Governorate(
                                    id = governorate.index.toString(),
                                    governorateNameAr = governorate.titleAr,
                                    governorateNameEn = governorate.titleEn
                                )

                                if (mutableGovernorate.id != newGovernorate.id) {
                                    mutableGovernorate = newGovernorate

                                    cityAndGovernorateViewModel.setCity(emptyList())
                                    mutableListCity = emptyList()
                                    defaultCitySelect =
                                        EntryModel(index = -1, titleAr = "", titleEn = "")
                                    userChangedCity = false

                                    isLoadingCities = true
                                    cityAndGovernorateViewModel.getCity(governorateId = mutableGovernorate.id)
                                }

                                if (isWrongGovernorate.isWrong) {
                                    isWrongGovernorate = WrongVerify()
                                }
                            },
                            label = stringResource(R.string.governorate),
                            wrong = isWrongGovernorate
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    // المدن
                    if (mutableListCity.isNotEmpty()) {
                        DropDawnSelect(
                            selectFirst = true,
                            list = mutableListCity,
                            defaultSelected = defaultCitySelect,
                            getSelected = { city ->
                                defaultCitySelect = city
                                userChangedCity = true
                            },
                            label = stringResource(R.string.city_or_center),
                            wrong = WrongVerify()
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    // اختيار الجنس
                    DropDawnSelect(
                        list = genderList,
                        defaultSelected = mutableGender,
                        getSelected = { gender ->
                            mutableGender = gender
                            // ممكن هنا تمسح أخطاء لو تستخدمها
                        },
                        label = stringResource(R.string.gender),
                        wrong = WrongVerify()
                    )

                    Spacer(Modifier.height(10.dp))

                    // اختيار الفريق (team)
                    DropDawnSelect(
                        list = teamList,
                        defaultSelected = mutableTeam,
                        getSelected = { team ->
                            mutableTeam = team
                            // ممكن هنا تمسح أخطاء لو تستخدمها
                        },
                        label = stringResource(R.string.team),
                        wrong = WrongVerify()
                    )

                    Spacer(Modifier.height(20.dp))

                    // Button Update
                    ButtonFill(
                        onClick = {

                           var challengeRequest = ChallengeRequest(
                                descriptionPost = defaultDescribe,
                                club = defaultClub,
                                team = mutableTeam.index + 1,
                                whatsUpNumber = defaultWhatsUp,
                                gender = Gender(
                                    index = mutableGender.index,
                                    genderAr = mutableGender.titleAr,
                                    genderEn = mutableGender.titleEn
                                ),
                                governorate = mutableGovernorate,
                                city = City(
                                    id = defaultCitySelect.index.toString(),
                                    governorate_id = defaultGovernorateSelect.index.toString(),
                                    city_name_ar = defaultCitySelect.titleAr.toString(),
                                    city_name_en = defaultCitySelect.titleEn.toString()
                                ),
                            )

                            val request = validateAddChallenge(
                                challengeRequest = challengeRequest,
                                setGenderError = { isWrongGender = it },
                                setWhatsUpError = { isWrongWhatsUp = it },
                                setGovernorateError = { isWrongGovernorate = it },
                                setCityError = { isWrongCity = it },
                                setTeamChallengeError = { isWrongChallengeTeam = it },
                                context = context
                            )


                            request?.let {
                                updateChallengeViewModel.updateChallenge(challengeID = challengeID, challengeRequest = it)
                            }
                        },
                        label = stringResource(R.string.update_challenge)
                    )
                }


            }

            //State Update New Challenge
            when (val state = stateUpdateChallenge) {

                //Loading
                is StateUpdateChallenge.Loading -> {
                    isProgress = true

                    LoadingDialog(message = stringResource(R.string.updating_challenge))

                }

                //Successful
                is StateUpdateChallenge.Success -> {
                    LoadingDialog(message = stringResource(id = R.string.challenge_update_successfully))

                    var challengeRequest = ChallengeRequest(
                        descriptionPost = defaultDescribe,
                        club = defaultClub,
                        team = mutableTeam.index + 1,
                        whatsUpNumber = defaultWhatsUp,
                        gender = Gender(
                            index = mutableGender.index,
                            genderAr = mutableGender.titleAr,
                            genderEn = mutableGender.titleEn
                        ),
                        governorate = mutableGovernorate,
                        city = City(
                            id = defaultCitySelect.index.toString(),
                            governorate_id = defaultGovernorateSelect.index.toString(),
                            city_name_ar = defaultCitySelect.titleAr.toString(),
                            city_name_en = defaultCitySelect.titleEn.toString()
                        ),
                    )
                        //Update Challenges in Home Page
                        LaunchedEffect(true) {
                            homeChallengesViewModel.updateChallenge(challengeID = challengeID, challengeRequest = challengeRequest)

                            Log.d("UpdatedChallenge", state.data.messageEn)
                                snackbarHostState.showSnackbar(
                                    if (ChangeLanguage.getSavedLanguage(context) == "ar") state.data.messageAr
                                    else state.data.messageEn
                                )


                                appNavController.popBackStack()

                                isProgress = false
                        }
                }


                //UnAuthorization
                is StateUpdateChallenge.UnAuthorization -> {
                    AlertDialog(
                        messageAlert = stringResource(R.string.session_expired_message),
                        titleButtonOne = stringResource(R.string.back_to_login_button),
                        isButtonOne = true,
                        onClickButtonOne = {

                            updateChallengeViewModel.resetState()

                            appNavController.navigate(Routes.authScreen){
                                popUpTo(0){inclusive = true}

                            }
                        },

                        )
                }


                // Failure Update Challenge
                is StateUpdateChallenge.Failure -> {

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

            if (isLoading) {
                LoadingDialog()
            }

            if (challengeState == GlobalState.ERROR){
                AlertDialog(
                    messageAlert = stringResource(R.string.check_internet),
                    onClickButtonOne = {
                        updateChallengeViewModel.getSingleChallenge(challengeID = challengeID)
                        cityAndGovernorateViewModel.getGovernorate()
                    },
                    titleButtonOne = stringResource(R.string.retry),
                    isButtonTwo = true,
                    onClickButtonTwo = {
                        appNavController.popBackStack()

                    },
                    titleButtonTwo = stringResource(R.string.back)
                )
            }

            if (isBack)
                AlertDialog(
                    messageAlert = stringResource(R.string.exit_alert_message),
                    isButtonOne = true,
                    isButtonTwo = true,
                    titleButtonOne = stringResource(R.string.yes),
                    titleButtonTwo = stringResource(R.string.no),
                    onClickButtonOne = {
                        appNavController.popBackStack()
                        isBack = false

                    },
                    onClickButtonTwo = {
                        isBack = false
                    }
                )

            SnackBar(snackBarHostState = snackbarHostState , modifier = Modifier.align(Alignment.BottomCenter))

        }
    }
}


package com.example.myapplication.presentation.screens.main.pages.setting_page.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.viewmodel.InformationUserViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.InputsComponents.InputText
import com.example.myapplication.presentation.components.LoadingDialog
import com.example.myapplication.utils.GlobalState
import com.example.myapplication.R
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.constant.ChangeLanguage

@Composable
fun InformationUserPage(
    navController: NavController,
    informationUserViewModel: InformationUserViewModel = hiltViewModel()
){
    val context = LocalContext.current

    val stateInformationUser by informationUserViewModel.stateFetchData.collectAsState()
    val informationUser by informationUserViewModel.informationUser.collectAsState()

    Scaffold(

    ){ innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
            ){

            Spacer(Modifier.height(20.dp))

            //Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                HeaderText(text = stringResource(R.string.my_information))

                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.graphicsLayer(rotationZ = 180f)

                    )
                }

            } //end Header

            Spacer(modifier = Modifier.height(20.dp))

            when(stateInformationUser){
                GlobalState.LOADING -> {
                    LoadingDialog()
                }

                GlobalState.READY -> {
                    informationUser?.let {
                        Column {

                            //Name
                            InputText(
                                isEdit = false,
                                getText = {

                                },
                                label = stringResource(R.string.full_name),
                                initialValue = it.fullName,
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            //Email
                            InputText(
                                getText = {

                                },
                                isEdit = false,
                                label = stringResource(R.string.email),
                                initialValue = it.email,
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            //Gender
                            InputText(
                                getText = {

                                },
                                isEdit = false,

                                label = stringResource(R.string.email),
                                initialValue = if (ChangeLanguage.getSavedLanguage(context) == "ar")
                                    it.gender.genderAr
                                else it.gender.genderEn
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            //Governorate
                            InputText(
                                getText = {

                                },
                                isEdit = false,

                                label = stringResource(R.string.governorate_filter),
                                initialValue = if (ChangeLanguage.getSavedLanguage(context) == "ar")
                                    it.governorate.governorateNameAr
                                else it.governorate.governorateNameEn
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            //City
                            InputText(
                                getText = {

                                },
                                isEdit = false,

                                label = stringResource(R.string.city_or_center_information),
                                initialValue = if (ChangeLanguage.getSavedLanguage(context) == "ar")
                                    it.city.city_name_ar
                                else it.city.city_name_en
                            )

                        }

                    }
                }

                GlobalState.Error -> {

                }

                GlobalState.EMPTY -> {

                }

                else -> {

                }
            }

        }
    }

}
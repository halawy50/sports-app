package com.example.myapplication.presentation.screens.onBoardingScreen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.HideStatusBar
import com.example.myapplication.presentation.components.ItemOnBoarding
import com.example.myapplication.presentation.constant.Routes
import com.example.myapplication.presentation.viewmodel.ConstantDataViewModel

@Composable
fun OnBoardingScreen(activity: Activity, navController: NavController , padding: PaddingValues, constantDataViewModel: ConstantDataViewModel = hiltViewModel()){

    val state = rememberPagerState{constantDataViewModel.onBoardingList.size}

    HideStatusBar(activity, textIsDark = false)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center
    ){
        HorizontalPager(state , modifier = Modifier.fillMaxSize()){ currentPage ->
            val isFinish = currentPage == state.pageCount - 1
            ItemOnBoarding(
                constantDataViewModel.onBoardingList[currentPage],
                isFinish = isFinish,
                pagerState = state,
                padding = padding,
                onFinish = {
                    navController.navigate(Routes.authScreen){
                        popUpTo(Routes.onBoardingScreen){inclusive = true}
                    }
                }
            )
        }
    }

}
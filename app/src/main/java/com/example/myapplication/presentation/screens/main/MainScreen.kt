package com.example.myapplication.presentation.screens.main

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.myapplication.domain.model.NavItem
import com.example.myapplication.R
import com.example.myapplication.presentation.components.HideStatusBar
import com.example.myapplication.presentation.screens.main.pages.HomePage
import com.example.myapplication.presentation.screens.main.pages.MessagePage
import com.example.myapplication.presentation.screens.main.pages.setting_page.SettingPage
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.gray
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.presentation.viewmodel.CityAndGovernorateViewModel
import com.example.myapplication.presentation.viewmodel.DeleteChallengeViewModel
import com.example.myapplication.presentation.viewmodel.FilterViewModel
import com.example.myapplication.presentation.viewmodel.HomeChallengesViewModel
import com.example.myapplication.presentation.viewmodel.LogOutViewModel
import com.example.myapplication.presentation.viewmodel.MainScreenViewModel
import com.example.myapplication.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
    activity: Activity,
    navController: NavController,
    homeChallengesViewModel: HomeChallengesViewModel,
    cityAndGovernorateViewModel: CityAndGovernorateViewModel = hiltViewModel(),
    filterViewModel: FilterViewModel = hiltViewModel(),
    logOutViewModel: LogOutViewModel = hiltViewModel()

){


    val selectedIndex by mainScreenViewModel.selectedIndex.collectAsState()
    HideStatusBar(activity = activity)
    val navItemList = listOf<NavItem>(
        NavItem(
            label = stringResource(R.string.home_label),
            icon = R.drawable.home,
            iconSelected = R.drawable.home_selected,
            badgeCount = 0,
        ),
//        NavItem(
//            label = stringResource(R.string.message_label),
//            icon = R.drawable.message,
//            iconSelected = R.drawable.message_selected,
//            badgeCount = 5,
//
//        ),
        NavItem(
            label = stringResource(R.string.setting_label),
            icon = R.drawable.user,
            iconSelected = R.drawable.user_selected,
            badgeCount = 0,
        ),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = white,
                modifier = Modifier
                    .shadow(20.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))

            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { mainScreenViewModel.setSelectIndex(index)},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = blue,
                            unselectedIconColor = gray,
                            selectedTextColor = blue,
                            unselectedTextColor = gray,
                            indicatorColor = Color.Transparent // خلفية العنصر المحدد
                        ),
                        icon = {
                            BadgedBox(badge = {
                                if(navItem.badgeCount>0){
                                    Badge(){
                                        Text(text = navItem.badgeCount.toString())
                                    }
                                }
                            }){
                                Icon(
                                    painter = painterResource(
                                        id = if (selectedIndex == index) navItem.iconSelected else navItem.icon
                                    ),
                                    contentDescription = navItem.label
                                )
                            }

                        },
                        label = { ParagraphText(text = navItem.label , color = if (selectedIndex == index) blue else gray) }
                    )
                }
            }

        }
    ){ innerPadding->
            ContentScree(
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
                    .padding(innerPadding),
                selectedIndex = selectedIndex,
                navController = navController,
                homeChallengesViewModel = homeChallengesViewModel,
                cityAndGovernorateViewModel = cityAndGovernorateViewModel,
                filterViewModel = filterViewModel,
                logOutViewModel = logOutViewModel
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentScree(modifier: Modifier,
                 selectedIndex: Int,
                 navController: NavController,
                 homeChallengesViewModel: HomeChallengesViewModel,
                 cityAndGovernorateViewModel: CityAndGovernorateViewModel,
                 filterViewModel: FilterViewModel,
                 logOutViewModel: LogOutViewModel


){


    Box(modifier = modifier){
            when(selectedIndex){
                0-> HomePage(
                    appNavController = navController,
                    homeChallengesViewModel = homeChallengesViewModel,
                    cityAndGovernorateViewModel = cityAndGovernorateViewModel,
                    filterViewModel = filterViewModel,

                )
//                1-> MessagePage()
                1-> SettingPage(navController = navController , logOutViewModel = logOutViewModel)
        }
    }
}
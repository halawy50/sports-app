package com.example.myapplication.presentation.screens.main

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.myapplication.presentation.screens.main.homePage.HomePage
import com.example.myapplication.presentation.screens.main.messagePage.MessagePage
import com.example.myapplication.presentation.screens.main.settingPage.SettingPage
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.gray
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.ui.theme.white

@Composable
fun MainScreen(
    activity: Activity
){
    HideStatusBar(activity = activity)
    var selectedIndex by remember { mutableStateOf(0) }
    val navItemList = listOf<NavItem>(
        NavItem(
            label = stringResource(R.string.home_label),
            icon = R.drawable.home,
            iconSelected = R.drawable.home_selected,
            badgeCount = 0,
        ),
        NavItem(
            label = stringResource(R.string.message_label),
            icon = R.drawable.message,
            iconSelected = R.drawable.message_selected,
            badgeCount = 5,

        ),
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
                        onClick = { selectedIndex = index },
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
                    .padding(end = 20.dp , start = 20.dp)
                    .padding(innerPadding),
                selectedIndex = selectedIndex
            )
    }
}

@Composable
fun ContentScree(modifier: Modifier, selectedIndex: Int){
    Box(modifier = modifier){
            when(selectedIndex){
                0-> HomePage()
                1-> MessagePage()
                2-> SettingPage()
        }
    }
}
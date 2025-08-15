package com.example.myapplication.presentation.screens.main.pages.setting_page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.R
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.constant.routes.Routes
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.ui.theme.red

@Composable
fun SettingPage(
    navController: NavController
){

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

            Column(
                modifier = Modifier.weight(0.7f),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(Modifier.height(30.dp))

                HeaderText(stringResource(R.string.setting))

                Spacer(Modifier.height(30.dp))

                //My Account
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(enabled = true , onClick = {
                        navController.navigate(Routes.informationUserPage)
                    }).padding(vertical = 10.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(painter = painterResource(R.drawable.user), contentDescription = "")
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = stringResource(id = R.string.mp_profile) , style = TextStyle(fontFamily = almarai_regular) )
                }


                Spacer(modifier = Modifier.height(10.dp))


                //My Challenges
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(enabled = true , onClick = {

                        navController.navigate(Routes.myChallengesPage)

                    }).padding(vertical = 10.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start

                ) {
                    Icon(painter = painterResource(R.drawable.property), contentDescription = "")
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = stringResource(id = R.string.my_challenges) , style = TextStyle(fontFamily = almarai_regular) )
                }


                Spacer(modifier = Modifier.height(10.dp))


                //Add New Challenges
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(enabled = true , onClick = {

                        navController.navigate(Routes.addNewChallengesPage)

                    }).padding(vertical = 10.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start

                ) {
                    Icon(painter = painterResource(R.drawable.add), contentDescription = "")
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = stringResource(id = R.string.add_new_challenge) , style = TextStyle(fontFamily = almarai_regular) )
                }


                Spacer(modifier = Modifier.height(10.dp))

                //Language
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(enabled = true , onClick = {
                        navController.navigate(Routes.changeLanguagePage)
                    }).padding(vertical = 10.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start

                ) {
                    Icon(painter = painterResource(R.drawable.language), contentDescription = "")
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = stringResource(id = R.string.language) , style = TextStyle(fontFamily = almarai_regular) )
                }

                Spacer(modifier = Modifier.height(10.dp))

                //Privacy Policy
                Row(
                    modifier = Modifier.fillMaxWidth().clickable(enabled = true , onClick = {

                    }).padding(vertical = 10.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start

                ) {
                    Icon(painter = painterResource(R.drawable.policy), contentDescription = "")
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = stringResource(id = R.string.privacy_policy) , style = TextStyle(fontFamily = almarai_regular) )
                }
            }

            Box(
                modifier = Modifier.weight(0.3f).padding(bottom = 20.dp),
                contentAlignment = Alignment.BottomCenter
            ){
                ButtonFill(
                    onClick = {

                    },
                    label = stringResource(R.string.sign_out)
                )
            }


    }
}
package com.example.myapplication.presentation.components.HomeComponents

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.domain.model.challenge_model.ChallengeResult
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.presentation.viewmodel.TokenManagerViewModel
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.ui.theme.black
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.ui.theme.white
import com.example.myapplication.utils.formatIsoDateToLocalShort

@SuppressLint("SuspiciousIndentation")
@Composable
fun ItemChallenger(
    onRemove: () -> Unit,
    onEdit: () -> Unit,
    challenge: ChallengeResult,
    tokenManagerViewModel: TokenManagerViewModel = hiltViewModel(),

){


    val tokenManager = tokenManagerViewModel.tokenManagerObserve
    var menuExpanded by remember { mutableStateOf(false) }


    val context = LocalContext.current

    val genderUserId = challenge.genderUserIndex

    Column {
        //Header(image, name, history, chat)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10000.dp))
                ){
                        if (genderUserId==0){
                            Image(
                                painter = painterResource(R.drawable.male_avatar),
                                contentDescription = "male_avatar.png",
                                contentScale = ContentScale.Crop
                            )
                        }else if(genderUserId==1){
                            Image(
                                painter = painterResource(R.drawable.female_avater),
                                contentDescription = "female_avater.png",
                                contentScale = ContentScale.Crop
                            )
                        }else{
                            Image(
                                painter = painterResource(R.drawable.account),
                                contentDescription = "account.png",
                                contentScale = ContentScale.Crop
                            )
                        }
                }

                Spacer(Modifier.width(8.dp))

                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    //name
                    Text(
                        text = challenge.namePlayer ,
                        style = TextStyle(
                            fontFamily = almarai_regular,
                            color = black,
                            fontSize = 16.sp,

                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )


                    //date public post
                    Text(
                        text = formatIsoDateToLocalShort(challenge.upload),
                        style = TextStyle(
                            fontFamily = almarai_regular,
                            color = gray,
                            fontSize = 12.sp
                        )
                    )
                }
            }


            Box(
                contentAlignment = Alignment.TopEnd,
            ){
                if (tokenManager.getUserId() == challenge.userFK) {
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            modifier = Modifier.background(color = white),

                            ) {
                            //edit
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.edit), style = TextStyle(fontFamily = almarai_regular)) },
                                onClick = {
                                    menuExpanded = false
                                    onEdit()

                                },
                                leadingIcon = {
                                    Icon(painter = painterResource(R.drawable.edit), contentDescription = null)
                                }
                            )

                            //remove
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.remove), style = TextStyle(fontFamily = almarai_regular)) },
                                onClick = {
                                    menuExpanded = false
                                    onRemove()
                                },
                                leadingIcon = {
                                    Icon(painter = painterResource(R.drawable.remove), contentDescription = null)
                                }
                            )
                        }

                    }

                }
                else {
                    // Show chat button only
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(1000.dp))
                            .clickable {
                                WhatsAppHelper.openWhatsAppChat(
                                    context = context,
                                    phoneNumber = challenge.whatsUpNumber,
                                    message = context.getString(R.string.whatsapp_message)
                                )
                            }

                    ) {
                        Icon(
                            modifier = Modifier.padding(10.dp),
                            painter = painterResource(R.drawable.chat_icon),
                            contentDescription = "chat",
                            tint = black
                        )
                    }
                }
            }




        }//end Header(image, name, history, chat)

//        if (!challenge.description.isBlank())
            Spacer(modifier = Modifier.height(15.dp))

        if (!challenge.description.isBlank())
        //description post
        Text(text = challenge.description ,
                 style = TextStyle(
                    fontSize = 16.sp,
                     lineHeight = 28.sp,
                     fontFamily = almarai_regular,
                     color = black
                )
        )//end description post

        if (!challenge.description.isBlank())
            Spacer(modifier = Modifier.height(20.dp))

        //Probabilities Post
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {


            //Football Team
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    painter = painterResource(R.drawable.footbal_team_icon),
                    contentDescription = "football_team_icon.png"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "${challenge.team} \u00D7 ${challenge.team}"
                )
            }

            //Club
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    painter = painterResource(R.drawable.club_icon),
                    contentDescription = "club_icon.png"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(text =if (!challenge.club.isBlank()) challenge.club else stringResource(R.string.undefined))
            }

            //Gender
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    painter = painterResource(R.drawable.gender_icon),
                    contentDescription = "gender_icon.png"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = stringResource(id = if (challenge.genderChallengeIndex == 0) R.string.gender_male else R.string.gender_female))
            }

            //Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.location_icon),
                    contentDescription = "location_icon.png"
                )

                Spacer(modifier = Modifier.width(10.dp))

                if (ChangeLanguage.getSavedLanguage(context = context )=="ar"){
                    Text(text = "${challenge.governorate.governorateNameAr} - ${challenge.city.city_name_ar}")
                }
                else
                Text(text = "${challenge.governorate.governorateNameEn} - ${challenge.city.city_name_en}")
            }


        } //end Probabilities Post

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(20.dp))



    }

}
package com.example.myapplication.presentation.components.HomeComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.ParagraphText

@Composable
fun ItemChallenger(){

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
            ) {
                Box(
                    modifier = Modifier.size(30.dp)
                ){
                    Image(
                        painter = painterResource(R.drawable.person) , contentDescription = "person.png"
                    )
                }

                Column{
                    HeaderText("محمد علي")
                    ParagraphText("4 مايو 2025 | 20:45 م")
                }
            }

            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "message"
                )
            }
        }

    }

}
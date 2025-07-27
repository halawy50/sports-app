package com.example.myapplication.presentation.components.HomeComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.GifFromDrawable

@Composable
fun EmptyChallenges(){
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)){
            GifFromDrawable(context , R.drawable.process)
        }

        Text(stringResource(R.string.empty_challenge))

        Spacer(Modifier.height(20.dp))
        ButtonFill(
            onClick = {

            },
            label = stringResource(id = R.string.add_new_challenge)
        )
    }


}
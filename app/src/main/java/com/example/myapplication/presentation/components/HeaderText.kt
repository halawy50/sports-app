package com.example.myapplication.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.almarai_extrabold
import com.example.myapplication.ui.theme.black

@Composable
fun HeaderText(text: String , textAlign: TextAlign = TextAlign.Center){
    Text(
        text = text,
        style = TextStyle(
            fontFamily = almarai_extrabold,
            color = black,
            fontSize = 18.sp
        ),
        textAlign = textAlign
    )
}
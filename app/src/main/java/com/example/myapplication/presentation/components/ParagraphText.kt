package com.example.myapplication.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.ui.theme.black


@Composable
fun ParagraphText(text: String , textAlign: TextAlign = TextAlign.Center){
    Text(
        text = text,
        style = TextStyle(
            fontFamily = almarai_regular,
            color = black,
            lineHeight = 28.sp
        ),
        textAlign = textAlign
    )
}
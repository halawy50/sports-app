package com.example.myapplication.presentation.components.ButtonsComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.almarai_light
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.white

@Composable
fun ButtonWithBorder(modifier: Modifier = Modifier.fillMaxWidth().height(55.dp) , onClick : ()->Unit , text : String){
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = white,
            contentColor = blue
        ),
        border = BorderStroke(color = blue , width = 1.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text,
            style = TextStyle(fontFamily = almarai_regular, fontSize = 14.sp))
    }

    Spacer(Modifier.height(10.dp))

}
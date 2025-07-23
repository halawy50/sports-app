package com.example.myapplication.presentation.components.ButtonsComponents

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.almarai_light
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.white

import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun ButtonFill(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(55.dp),
    onClick: () -> Unit,
    text: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Button(
        modifier = modifier,
        onClick = {
            keyboardController?.hide() // إخفاء الكيبورد
            onClick() // تنفيذ الإجراء المطلوب
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = blue,
            contentColor = white
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = almarai_regular,
                fontSize = 14.sp
            )
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
}

package com.example.myapplication.presentation.components.InputsComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.myapplication.ui.theme.almarai_light
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.ui.theme.blue
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.ui.theme.red

@Composable
fun InputNumber(
    getNumber: (number: String) -> Unit ,
    label: String,
    wrong: WrongVerify = WrongVerify()
) {
    val focusManager = LocalFocusManager.current
    var number by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    Column {

    OutlinedTextField(
        value = number,
        isError = wrong.isWrong,

        onValueChange = {
            number = it
            getNumber(it)
        },
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = if (wrong.isWrong) red else if(isFocused&&!wrong.isWrong) blue else gray,
                    fontFamily = almarai_light,
                    fontSize = if(isFocused || number.isNotEmpty()) 12.sp else 16.sp,
                ),
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        shape = RoundedCornerShape(10),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (wrong.isWrong) red else blue,
            unfocusedBorderColor = if (wrong.isWrong) red else gray,
            cursorColor = if (wrong.isWrong) red else blue,
            errorBorderColor = red
            )
        )

        // ✅ إظهار رسالة الخطأ أسفل الحقل
        if (wrong.isWrong) {
            Text(
                text = wrong.message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 10.sp,
                fontFamily = almarai_light
            )
        }

  }

}
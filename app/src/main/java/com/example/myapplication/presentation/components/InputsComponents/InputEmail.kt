package com.example.myapplication.presentation.components.InputsComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.myapplication.R
import com.example.myapplication.ui.theme.almarai_light
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.ui.theme.blue
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.ui.theme.red

@Composable
fun InputEmail(
    getEmail: (email: String) -> Unit ,
    wrong: WrongVerify = WrongVerify()
) {
    val focusManager = LocalFocusManager.current
    var phoneNumber by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

   Column {

    OutlinedTextField(
        value = phoneNumber,
        isError = wrong.isWrong,

        onValueChange = {
            phoneNumber = it
            getEmail(it)
        },
        label = {
            Text(
                text = stringResource(R.string.email),
                style = TextStyle(
                    color = if (wrong.isWrong) red else if(isFocused&&!wrong.isWrong) blue else gray,
                    fontFamily = almarai_light,
                    fontSize = if(isFocused || phoneNumber.isNotEmpty()) 12.sp else 16.sp
                )
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
            keyboardType = KeyboardType.Email,
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
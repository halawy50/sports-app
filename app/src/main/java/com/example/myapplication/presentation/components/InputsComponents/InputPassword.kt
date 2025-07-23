package com.example.myapplication.presentation.components.InputsComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.ui.theme.almarai_light
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.ui.theme.red


@Composable
fun InputPassword(
    getPassword: (String) -> Unit ,
    label: String = stringResource(R.string.password),
    isNext: Boolean = false,
    wrong: WrongVerify = WrongVerify()
) {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    Column {

        OutlinedTextField(
            value = password,
            isError = wrong.isWrong,
            onValueChange = {
                password = it
                getPassword(it)
            },
            label = {
                Text(
                    text = label,
                    style = TextStyle(
                        color = if (wrong.isWrong) red else if(isFocused&&!wrong.isWrong) blue else gray,
                        fontFamily = almarai_light,
                        fontSize = if (isFocused || password.isNotEmpty()) 12.sp else 16.sp
                    )
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = if (isNext) ImeAction.Next else ImeAction.Done
            ),
            trailingIcon = {
                val image = if (passwordVisible) R.drawable.eye else R.drawable.eye_off
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = image),
                        contentDescription = description
                    )
                }
            },
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

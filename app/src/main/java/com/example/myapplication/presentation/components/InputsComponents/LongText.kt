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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LongText(
    getText: (text: String) -> Unit,
    label: String,
    initialValue: String = "",
    wrong: WrongVerify = WrongVerify()
) {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf(initialValue) }
    var isFocused by remember { mutableStateOf(false) }

    // Update text when initialValue changes
    LaunchedEffect(initialValue) {
        if (text != initialValue) {
            text = initialValue
        }
    }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                getText(it)
            },
            maxLines = 5,
            minLines = 5,

            label = {
                Text(
                    text = label,
                    style = TextStyle(
                        color = if (wrong.isWrong) red else if(isFocused&&!wrong.isWrong) blue else gray,
                        fontFamily = almarai_light,
                        fontSize = if (isFocused || text.isNotEmpty()) 12.sp else 16.sp
                    )
                )
            },
            isError = wrong.isWrong,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            shape = RoundedCornerShape(5),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (wrong.isWrong) red else blue,
                unfocusedBorderColor = if (wrong.isWrong) red else gray,
                cursorColor = if (wrong.isWrong) red else blue,
                errorBorderColor = red
            )
        )

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



//package com.example.myapplication.presentation.components.InputsComponents
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import com.example.myapplication.R
//import com.example.myapplication.ui.theme.almarai_light
//import com.example.myapplication.ui.theme.gray
//import com.example.myapplication.ui.theme.blue
//import androidx.compose.ui.focus.FocusDirection
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.unit.sp
//import com.example.myapplication.domain.model.WrongVerify
//import com.example.myapplication.ui.theme.red
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun LongText(
//    getText: (text: String) -> Unit,
//    label: String,
//    wrong: WrongVerify = WrongVerify()
//) {
//    val focusManager = LocalFocusManager.current
//    var text by remember { mutableStateOf("") }
//    var isFocused by remember { mutableStateOf(false) }
//
//    Column {
//        OutlinedTextField(
//            value = text,
//            onValueChange = {
//                text = it
//                getText(it)
//            },
//            maxLines = 5,
//            minLines = 5,
//
//            label = {
//                Text(
//                    text = label,
//                    style = TextStyle(
//                        color = if (wrong.isWrong) red else if(isFocused&&!wrong.isWrong) blue else gray,
//                        fontFamily = almarai_light,
//                        fontSize = if (isFocused || text.isNotEmpty()) 12.sp else 16.sp
//                    )
//                )
//            },
////            singleLine = true,
//            isError = wrong.isWrong,
//            modifier = Modifier
//                .fillMaxWidth()
//                .onFocusChanged { focusState ->
//                    isFocused = focusState.isFocused
//                }
//            ,
//            shape = RoundedCornerShape(5),
//
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = if (wrong.isWrong) red else blue,
//                unfocusedBorderColor = if (wrong.isWrong) red else gray,
//                cursorColor = if (wrong.isWrong) red else blue,
//                errorBorderColor = red
//            )
//        )
//
//        if (wrong.isWrong) {
//            Text(
//                text = wrong.message,
//                color = MaterialTheme.colorScheme.error,
//                fontSize = 10.sp,
//                fontFamily = almarai_light
//            )
//        }
//    }
//}

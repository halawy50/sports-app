package com.example.myapplication.presentation.components.InputsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.model.EntryModel
import com.example.myapplication.domain.model.WrongVerify
import com.example.myapplication.ui.theme.almarai_light
import com.example.myapplication.ui.theme.almarai_regular
import com.example.myapplication.ui.theme.black
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.gray
import com.example.myapplication.ui.theme.red
import com.example.myapplication.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDawnSelect(
    list : List<EntryModel>,
    getSelected: (select: EntryModel) -> Unit,
    label: String,
    wrong: WrongVerify = WrongVerify()

) {
    val focusManager = LocalFocusManager.current

    var selectedOption by remember { mutableStateOf(list[0]) }
    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    Column {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption.title,
            onValueChange = {},
            readOnly = true,
            isError = wrong.isWrong,

            label = { Text(
                label,
                style = TextStyle(
                    color = if (wrong.isWrong) red else if(isFocused&&!wrong.isWrong) blue else gray,
                    fontFamily = almarai_light,
                    fontSize = if (isFocused || selectedOption.title.isNotEmpty()) 12.sp else 16.sp
                )
                ) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Drop-down icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(color = white),

        ) {
            list.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option.title,
                            style = TextStyle(
                                color = black,
                                fontFamily = almarai_regular,
                                fontSize = 16.sp
                            )
                        )
                    },
                    onClick = {
                        selectedOption = option
                        getSelected(option)
                        expanded = false
                        focusManager.clearFocus()
                    },
                )
            }

        }

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
}
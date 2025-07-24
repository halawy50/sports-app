package com.example.myapplication.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.black
import com.example.myapplication.ui.theme.white

@Composable
fun SnackBar(snackBarHostState : SnackbarHostState, modifier: Modifier){
    //SnackBar
    SnackbarHost(
        hostState = snackBarHostState,
        modifier = modifier,
        snackbar = { data ->
            Snackbar(
                snackbarData = data,
                containerColor = black,  // لون الخلفية
                contentColor = white,         // لون النص
                shape = RoundedCornerShape(10.dp),  // شكل الزوايا
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    )
}
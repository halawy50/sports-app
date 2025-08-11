package com.example.myapplication.presentation.screens.main.messagePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.components.GifFromDrawable
import com.example.myapplication.presentation.components.HeaderText
import com.example.myapplication.presentation.components.ParagraphText
import com.example.myapplication.R
@Composable
fun MessagePage(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Box(modifier = Modifier.fillMaxWidth().height(300.dp)){
            GifFromDrawable(context , R.drawable.process)
        }
        HeaderText(text = "صفحة الرسائل")
        Spacer(Modifier.height(10.dp))
        ParagraphText(text = "حاليا يتم تنفيذ باقي المشروع")
    }
}
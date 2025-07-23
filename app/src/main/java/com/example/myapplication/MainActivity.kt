package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.myapplication.presentation.navController.NavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.presentation.constant.ChangeLanguage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavController(this@MainActivity, padding = innerPadding)
                }
            }
        }
    }


    override fun attachBaseContext(newBase: Context) {
        val language = ChangeLanguage.getSavedLanguage(newBase) // from SharedPreferences
        val context = ChangeLanguage.setLocale(newBase, language)
        super.attachBaseContext(context)
    }



}

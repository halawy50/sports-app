package com.example.myapplication.presentation.screens.changeLanguageScreen

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.HideStatusBar
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.presentation.constant.routes.Routes

@Composable
fun ChangeLanguageScreen(
    activity: MainActivity,
    padding: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current
    val languages = listOf("ar" to "العربية", "en" to "English")
    val savedLang = ChangeLanguage.getSavedLanguage(context)
    var selected by remember { mutableStateOf(savedLang) }

    HideStatusBar(activity)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 15.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column{
            Text(
                text = context.getString(R.string.choose_language),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            languages.forEach { (langCode, langName) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            selected = langCode

                            ChangeLanguage.saveLanguage(context, langCode)
                            ChangeLanguage.setLocale(context, langCode)

                            val activity = context as? Activity
                            activity?.recreate()
                        }
                ) {
                    RadioButton(
                        selected = (langCode == selected),
                        onClick = {
                            selected = langCode

                            ChangeLanguage.saveLanguage(context, langCode)
                            ChangeLanguage.setLocale(context, langCode)

                            val activity = context as? Activity
                            activity?.recreate()
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = langName)
                }
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            ButtonFill(
                onClick = {
                    navController.navigate(Routes.mainScreen){
                        popUpTo(0){inclusive=true}
                    }
                },
                label = context.getString(R.string.next)
            )
        }


    }
}

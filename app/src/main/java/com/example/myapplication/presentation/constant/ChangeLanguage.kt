package com.example.myapplication.presentation.constant

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale
import androidx.core.content.edit

object ChangeLanguage {
    @SuppressLint("ObsoleteSdkInt")
    fun setLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }

    fun saveLanguage(context: Context, language: String) {
        context.getSharedPreferences("LANGUAGE_SETTING", Context.MODE_PRIVATE)
            .edit {
                putString("language", language)
            }
    }

    fun getSavedLanguage(context: Context): String {
        return context.getSharedPreferences("LANGUAGE_SETTING", Context.MODE_PRIVATE)
            .getString("language", "en") ?: "en"
    }

}
package com.example.myapplication.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // Getter
    fun getIsFirstTimeLaunch(): Boolean {
        return prefs.getBoolean("is_first_time", false)
    }

    // Setter
    fun setIsFirstTimeLaunch(value: Boolean) {
        prefs.edit().putBoolean("is_first_time", value).apply()
    }
}

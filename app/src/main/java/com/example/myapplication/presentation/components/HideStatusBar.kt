package com.example.myapplication.presentation.components

import android.app.Activity
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat


@Composable
fun HideStatusBar(activity: Activity , textIsDark: Boolean = true){
    // جعل شريط الحالة شفاف
    activity.window.statusBarColor = ContextCompat.getColor(activity, android.R.color.transparent)
    activity.window.navigationBarColor = ContextCompat.getColor(activity, android.R.color.transparent)
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        activity.window.isStatusBarContrastEnforced = false
        activity.window.isNavigationBarContrastEnforced = false
    }

    // جعل الأيقونات والنص في شريط الحالة/navigation داكنة (لخلفية فاتحة)
    val windowInsetsController = WindowInsetsControllerCompat(activity.window, activity.window.decorView)
    windowInsetsController.isAppearanceLightStatusBars = textIsDark // أيقونات شريط الحالة داكنة
    windowInsetsController.isAppearanceLightNavigationBars = textIsDark // أيقونات شريط التنقل داكنة
}
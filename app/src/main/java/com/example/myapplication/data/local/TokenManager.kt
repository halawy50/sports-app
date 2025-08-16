package com.example.myapplication.data.local

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext private val context: Context) {

    fun saveLoginToken(userId: String, refreshToken: String, accessToken: String) {
        context.getSharedPreferences("TOKEN_MANAGER", Context.MODE_PRIVATE)
            .edit {
                putString("user_id", userId)
                putString("refresh_token", refreshToken)
                putString("access_token", accessToken)
            }
    }

    fun saveAccessToken(accessToken: String) {
        context.getSharedPreferences("TOKEN_MANAGER", Context.MODE_PRIVATE)
            .edit {
                putString("access_token", accessToken)
            }
    }

    fun saveRefreshToken(refreshToken: String) {
        context.getSharedPreferences("TOKEN_MANAGER", Context.MODE_PRIVATE)
            .edit {
                putString("refresh_token", refreshToken)
            }
    }

    fun removeAccessAndRefreshToken() {
        context.getSharedPreferences("TOKEN_MANAGER", Context.MODE_PRIVATE)
            .edit {
                putString("refresh_token", "")
                putString("access_token", "")
            }
    }

    // Get UserId
    fun getUserId(): String? {
        return context.getSharedPreferences("TOKEN_MANAGER", Context.MODE_PRIVATE)
            .getString("user_id", null)
    }


    // Get access token
    fun getAccessToken(): String? {
        return context.getSharedPreferences("TOKEN_MANAGER", Context.MODE_PRIVATE)
            .getString("access_token", null)
    }



    // Get refresh token
    fun getRefreshToken(): String? {
        return context.getSharedPreferences("TOKEN_MANAGER", Context.MODE_PRIVATE)
            .getString("refresh_token", null)
    }
}

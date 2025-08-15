package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TokenManagerViewModel @Inject constructor(private val tokenManager: TokenManager): ViewModel() {
    val tokenManagerObserve = tokenManager
}
package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.LocalManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocalManagerViewModel @Inject constructor(private val localManager: LocalManager): ViewModel() {
    val localManagerObserve = localManager
}
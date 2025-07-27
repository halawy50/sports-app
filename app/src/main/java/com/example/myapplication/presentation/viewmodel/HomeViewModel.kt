package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.utils.FetchDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _fetchDataState = MutableStateFlow(FetchDataState.LOADING)
    val fetchDataState : StateFlow<FetchDataState> = _fetchDataState

    init {
        changeFetchDataState()
    }

    private fun changeFetchDataState() {
        viewModelScope.launch {
            delay(2000)
            _fetchDataState.value = FetchDataState.EMPTY
        }
    }
}

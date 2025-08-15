package com.example.myapplication.utils

import com.example.myapplication.domain.model.Governorate

sealed class StateGovernorate {
    object Idle : StateGovernorate()
    object Loading : StateGovernorate()
    data class Success(val data: List<Governorate>) : StateGovernorate()
    data class Failure(val data: List<Governorate>) : StateGovernorate()
}

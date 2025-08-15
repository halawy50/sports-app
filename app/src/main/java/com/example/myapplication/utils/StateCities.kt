package com.example.myapplication.utils

import com.example.myapplication.domain.model.City

sealed class StateCities {
    object Idle : StateCities()
    object Loading : StateCities()
    data class Success(val data: List<City>) : StateCities()
    data class Failure(val data: List<City>) : StateCities()
}

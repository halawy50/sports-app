package com.example.myapplication.domain.model.challenge_model

import kotlinx.serialization.Serializable

@Serializable
data class FilterRequest(
    val cityIdsList: List<String> = emptyList(),
    val gendersIdsList: List<Int> = emptyList(),
    val teamList: List<Int> = emptyList(),
    val sortOrder: Boolean = true,
)

package com.example.myapplication.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Gender (
    val index: Int,
    val gender: String,
)
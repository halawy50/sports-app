package com.example.myapplication.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class City (
    val index: Int,
    val city: String,
)
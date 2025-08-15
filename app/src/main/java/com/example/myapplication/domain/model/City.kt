package com.example.myapplication.domain.model

@kotlinx.serialization.Serializable
data class City(
    val id: String,
    val governorate_id: String,
    val city_name_ar: String,
    val city_name_en: String
)
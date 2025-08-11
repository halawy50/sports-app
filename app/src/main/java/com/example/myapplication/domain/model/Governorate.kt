package com.example.myapplication.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GovernorateItem(
    @SerialName("governorate_name_ar")
    val governorateNameAr: String,
    @SerialName("governorate_name_en")
    val governorateNameEn: String,
    @SerialName("id")
    val id: String
)
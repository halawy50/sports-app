package com.example.myapplication.domain.model

import com.google.gson.annotations.SerializedName

data class Governorate(
    @SerializedName("governorate_name_ar")
    val governorateNameAr: String,
    @SerializedName("governorate_name_en")
    val governorateNameEn: String,
    @SerializedName("id")
    val id: String
)
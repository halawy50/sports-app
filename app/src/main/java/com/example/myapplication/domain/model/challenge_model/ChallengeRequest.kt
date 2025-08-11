package com.example.myapplication.domain.model.challenge_model


import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.Governorate
import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val city: City,
    val club: String,
    val descriptionPost: String,
    val gender: Gender,
    val governorate: Governorate,
    val team: Int
)
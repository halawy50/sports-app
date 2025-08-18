package com.example.myapplication.domain.model.challenge_model

import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Gender
import com.example.myapplication.domain.model.Governorate

data class ChallengeDataUpdate(
    val description : String,
    val club : String,
    val whatsUpNumber : String,
    val team : Int,
    val gender : Gender,
    val governorate : Governorate,
    val city: City
)

package com.example.persentation.domain.data.post_model

import com.example.myapplication.domain.model.challenge_model.Challenge
import kotlinx.serialization.Serializable

@Serializable
data class FilterResponse(
    val results: List<Challenge>,
    val currentPage: Int,
    val totalPages: Int,
    val totalCount: Long
)
package com.example.myapplication.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OtpData(
    val otp: String,
    var expiresAt: Long,
    var verified: Boolean = false,
    var removalJob: kotlinx.coroutines.Job? = null
)

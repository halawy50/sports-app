package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.InformationUser
import retrofit2.Response

interface InformationUserRepository {
    suspend fun informationUser(userId: String): Response<InformationUser>
}
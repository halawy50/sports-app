package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.AuthService
import com.example.myapplication.domain.model.InformationUser
import com.example.myapplication.domain.repository.InformationUserRepository
import retrofit2.Response
import javax.inject.Inject

class InformationUserRepositoryImpl @Inject constructor(private val authService: AuthService):InformationUserRepository {
    override suspend fun informationUser(userId: String): Response<InformationUser> {
        return authService.informationUser(userId = userId)
    }
}
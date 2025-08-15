package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.InformationUserRepository

class InformationUserUseCase(private val informationUserRepository: InformationUserRepository) {
    suspend operator fun invoke(userId: String) = informationUserRepository.informationUser(userId = userId)
}
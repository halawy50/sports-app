package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.register_model.RegisterRequest
import com.example.myapplication.domain.repository.RegisterRepository


class RegisterUseCase(
    private val registerRepository: RegisterRepository
){
    suspend operator fun invoke(registerRequest: RegisterRequest) = registerRepository.register(registerRequest = registerRequest)
}
package com.example.instagramapp.sign_screen.domain.usecase

import com.example.instagramapp.login_screen.domain.models.User
import com.example.instagramapp.sign_screen.domain.repository.SignRepository
import javax.inject.Inject

class SignUseCase @Inject constructor(private val repository: SignRepository) {
    suspend fun execute(user: User) = repository.sign(user = user)
}
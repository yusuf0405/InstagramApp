package com.example.instagramapp.sign_screen.domain.repository

import com.example.instagramapp.login_screen.domain.models.User
import retrofit2.Response

interface SignRepository {
    suspend fun sign(
        user: User
    ): Response<User>
}
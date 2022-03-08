package com.example.instagramapp.login_screen.domain.repository

import com.parse.ParseUser
import retrofit2.Response

interface LoginRepository {
    suspend fun login(
        email: String,
        password: String,
    ): Response<ParseUser>
}
package com.example.instagramapp.login_screen.domain.usecase

import com.example.instagramapp.login_screen.domain.repository.LoginRepository
import com.parse.ParseUser
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend fun execute(
        email: String,
        password: String,
    ):Response<ParseUser> = repository.login(
        email = email,
        password = password
    )
}
package com.example.instagramapp.sign_screen.data

import com.example.instagramapp.login_screen.domain.models.User
import com.example.instagramapp.app.network.api.UserApi
import com.example.instagramapp.app.utils.Cons.Companion.CONTENT_TYPE
import com.example.instagramapp.sign_screen.domain.repository.SignRepository
import retrofit2.Response
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(private val userApi: UserApi) : SignRepository {
    override suspend fun sign(user: User): Response<User> {
        return userApi.signUp(1, CONTENT_TYPE, user)
    }
}

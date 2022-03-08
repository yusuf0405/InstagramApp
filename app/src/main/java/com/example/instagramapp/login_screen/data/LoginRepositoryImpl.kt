package com.example.instagramapp.login_screen.data

import com.example.instagramapp.app.network.api.UserApi
import com.example.instagramapp.login_screen.domain.repository.LoginRepository
import com.parse.ParseUser
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Response
import javax.inject.Inject

@DelicateCoroutinesApi
class LoginRepositoryImpl @Inject constructor(private val userApi: UserApi) : LoginRepository {
    override suspend fun login(email: String, password: String):Response<ParseUser>{
        ParseUser.logIn(email,password)
        return userApi.login(1, username = email, password = password)
    }


}
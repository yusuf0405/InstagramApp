package com.example.instagramapp.app.network.api

import com.example.instagramapp.login_screen.domain.models.User
import com.parse.ParseUser
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("login")
    suspend fun login(
        @Header("X-Parse-Revocable-Session") session: Int,
        @Query("username") username: String,
        @Query("password") password: String,
    ): Response<ParseUser>

    @POST("users")
    suspend fun signUp(
        @Header("X-Parse-Revocable-Session") session: Int,
        @Header("Content-Type") type: String,
        @Body user: User,
    ): Response<User>

    @PUT("users/{ObjectId}")
    suspend fun updateUser(
        @Header("X-Parse-Session-Token") token: String,
        @Header("Content-Type") type: String,
        @Path("ObjectId") ObjectId: String,
        @Body user: User,
    ): Response<ParseUser>
}
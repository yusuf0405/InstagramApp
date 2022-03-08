package com.example.instagramapp.home_screen.domain.repository

import com.example.instagramapp.app.network.models.PostsDto
import retrofit2.Response

interface HomeRepository {

    suspend fun getAllPosts(): Response<PostsDto>
}
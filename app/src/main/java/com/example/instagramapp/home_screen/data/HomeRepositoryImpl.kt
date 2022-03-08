package com.example.instagramapp.home_screen.data

import com.example.instagramapp.app.network.api.PostApi
import com.example.instagramapp.app.network.models.PostsDto
import com.example.instagramapp.home_screen.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val postApi: PostApi) : HomeRepository {
    override suspend fun getAllPosts(): Response<PostsDto> = postApi.getAllPosts()
}
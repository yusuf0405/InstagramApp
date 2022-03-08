package com.example.instagramapp.app.network.api

import com.example.instagramapp.app.network.models.PostDto
import com.example.instagramapp.app.network.models.PostsDto
import retrofit2.Response
import retrofit2.http.*

interface PostApi {

    @GET("classes/Posts")
    suspend fun getAllPosts(): Response<PostsDto>

    @POST("classes/Posts")
    suspend fun postData(
        @Header("Content-Type") type: String,
        @Body post: PostDto,
    ): Response<PostDto>
}
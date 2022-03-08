package com.example.instagramapp.post_add_screen.domain.repository

import com.example.instagramapp.app.network.models.PostDto
import retrofit2.Response

interface AddPostRepository {

    suspend fun createNewPost(post: PostDto): Response<PostDto>
}
package com.example.instagramapp.post_add_screen.domain.usecase

import com.example.instagramapp.app.network.models.PostDto
import com.example.instagramapp.post_add_screen.domain.repository.AddPostRepository
import retrofit2.Response
import javax.inject.Inject

class CreateNewPostUseCase @Inject constructor(private val repository: AddPostRepository) {

    suspend fun execute(postDto: PostDto): Response<PostDto> =
        repository.createNewPost(post = postDto)
}
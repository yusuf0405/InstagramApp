package com.example.instagramapp.home_screen.domain.usecase

import com.example.instagramapp.app.network.models.PostsDto
import com.example.instagramapp.home_screen.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: HomeRepository,
) {
    suspend fun execute(): Response<PostsDto> = repository.getAllPosts()

}
package com.example.instagramapp.post_add_screen.data

import com.example.instagramapp.app.network.api.PostApi
import com.example.instagramapp.app.network.models.PostDto
import com.example.instagramapp.app.utils.Cons.Companion.CONTENT_TYPE
import com.example.instagramapp.post_add_screen.domain.repository.AddPostRepository
import retrofit2.Response
import javax.inject.Inject


class AddPostRepositoryImpl @Inject constructor(private val postApi: PostApi) : AddPostRepository {

    override suspend fun createNewPost(post: PostDto): Response<PostDto> =
        postApi.postData(CONTENT_TYPE, post)


}
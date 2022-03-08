package com.example.instagramapp.app.network.models


import com.google.gson.annotations.SerializedName

data class PostsDto(
    @SerializedName("results") var results: List<PostDto>
)
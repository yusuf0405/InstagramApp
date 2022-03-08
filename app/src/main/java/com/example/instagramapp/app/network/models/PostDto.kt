package com.example.instagramapp.app.network.models


import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("description") var description: String,
    @SerializedName("image") var image: ImageDto,
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
    @SerializedName("personId") var personId: String,
    @SerializedName("title") var title: String,
    @SerializedName("username") var username: String
)
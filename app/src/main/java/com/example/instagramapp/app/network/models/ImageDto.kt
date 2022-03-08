package com.example.instagramapp.app.network.models


import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("name") var name: String,
    @SerializedName("__type") var type: String,
    @SerializedName("url") var url: String
)
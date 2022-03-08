package com.example.instagramapp.home_screen.domain.models

import com.parse.ParseFile

data class Post(
    val personId: String,
    val username:String,
    var title: String,
    var description: String,
    var image: PostImage,
    var latitude: Double,
    var longitude: Double,
)
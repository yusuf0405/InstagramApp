package com.example.instagramapp.home_screen.domain.models

import com.google.gson.annotations.SerializedName

data class PostImage(
    var name: String,
    var type: String,
    var url: String
)
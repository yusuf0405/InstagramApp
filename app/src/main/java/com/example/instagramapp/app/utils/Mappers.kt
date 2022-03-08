package com.example.instagramapp.app.utils

import com.example.instagramapp.app.network.models.ImageDto
import com.example.instagramapp.app.network.models.PostDto
import com.example.instagramapp.home_screen.domain.models.Post
import com.example.instagramapp.home_screen.domain.models.PostImage
import com.parse.ParseFile

internal fun PostDto.toPosts(): Post {
    return Post(
        personId = personId,
        username = username,
        title = title,
        description = description,
        image = image.toImage(),
        latitude = latitude,
        longitude = longitude,
    )
}

internal fun ParseFile.toImage(): ImageDto {
    return ImageDto(
        name = name,
        type = "File",
        url = url,
    )
}

private fun ImageDto.toImage(): PostImage {
    return PostImage(name = name, url = url, type = type)
}

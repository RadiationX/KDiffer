package ru.radiationx.kdiffersample.data.entity

data class PostUpdateEntity(
    val postId: String,
    val likes: Int,
    val comments: Int,
    val saves: Int,
    val views: Int,
    val liked: Boolean,
    val commented: Boolean,
    val saved: Boolean,
)

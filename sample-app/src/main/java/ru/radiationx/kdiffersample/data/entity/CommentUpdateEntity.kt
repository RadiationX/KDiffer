package ru.radiationx.kdiffersample.data.entity

data class CommentUpdateEntity(
    val id: String,
    val postId: String,
    val likes: Int,
    val liked: Boolean
)

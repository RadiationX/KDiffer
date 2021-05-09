package ru.radiationx.kdiffersample.data.entity

data class CommentEntity(
    val id: String,
    val postId: String,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val likes: Int,
    val liked: Boolean
)

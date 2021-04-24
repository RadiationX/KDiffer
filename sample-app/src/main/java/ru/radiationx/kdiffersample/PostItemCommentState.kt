package ru.radiationx.kdiffersample

data class PostItemCommentState(
    val id: String,
    val authorName: String,
    val commentText: String,
    val likes: Int,
    val liked: Boolean
)

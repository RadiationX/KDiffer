package ru.radiationx.kdiffersample

data class PostItemFooterState(
    val likes: String,
    val comments: String,
    val saves: String,
    val views: String,
    val liked: Boolean,
    val commented: Boolean,
    val saved: Boolean
)
package ru.radiationx.kdiffersample

data class PostItemFooterState(
    val likes: Int,
    val comments: Int,
    val saves: Int,
    val views: Int,
    val liked: Boolean,
    val commented: Boolean,
    val saved: Boolean
)
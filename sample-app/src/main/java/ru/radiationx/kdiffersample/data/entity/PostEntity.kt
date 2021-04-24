package ru.radiationx.kdiffersample.data.entity

import java.util.*

data class PostEntity(
    val id: String,
    val authorName: String,
    val authorAvatarUrl: String,
    val groupName: String,
    val date: Date,
    val contentText: String?,
    val contentImage: String?,
    val likes: Int,
    val comments: Int,
    val saves: Int,
    val views: Int,
    val liked: Boolean,
    val commented: Boolean,
    val saved: Boolean,
    val comment: CommentEntity?
)

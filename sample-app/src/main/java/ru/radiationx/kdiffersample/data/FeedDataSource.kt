package ru.radiationx.kdiffersample.data

import ru.radiationx.kdiffersample.data.entity.PostEntity
import java.util.*

class FeedDataSource {

    suspend fun getPosts(): List<PostEntity> {
        return (1 until 10).map { createPost(it) }
    }

    private fun createPost(index: Int) = PostEntity(
        id = "post_$index",
        authorName = "Author Name $index",
        authorAvatarUrl = "url",
        groupName = "Group Name $index",
        date = Date(),
        contentText = null,
        contentImage = null,
        likes = 0,
        comments = 0,
        saves = 0,
        views = 0,
        liked = false,
        commented = false,
        saved = false,
        comment = null
    )

}
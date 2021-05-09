package ru.radiationx.kdiffersample.data

import ru.radiationx.kdiffersample.data.entity.CommentEntity
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
        contentText = "Hello there in post $index. Watch <a href=\"https://google.com/\">this awesome link</a> and how beautiful this <b>rich<i>text<u>in post</u></i></b>.",
        contentImage = null,
        likes = 0,
        comments = 0,
        saves = 0,
        views = 0,
        liked = false,
        commented = false,
        saved = false,
        comment = CommentEntity(
            id = "post_comment_$index",
            postId = "post_$index",
            authorName = "Another author $index",
            authorAvatarUrl = "url",
            commentText = "Comment with <a href=\"https://google.com/\">awesome link</a> and <b>rich<i>text<u>in comment</u></i></b>.",
            likes = 0,
            liked = false
        )
    )

}
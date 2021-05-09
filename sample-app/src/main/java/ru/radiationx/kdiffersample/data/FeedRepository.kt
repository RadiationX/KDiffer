package ru.radiationx.kdiffersample.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import ru.radiationx.kdiffersample.data.entity.CommentEntity
import ru.radiationx.kdiffersample.data.entity.PostEntity
import java.util.*
import kotlin.concurrent.fixedRateTimer

class FeedRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val posts = (1 until 25).map {
        createPost(it)
    }

    private val updatesState = MutableStateFlow(posts)

    private var timer: Timer? = null

    fun initTimer() {
        cancelTimer()
        timer = fixedRateTimer(period = (1000 / 10f).toLong()) {
            updatePosts()
        }
    }

    fun cancelTimer() {
        timer?.cancel()
        timer?.purge()
        timer = null
    }

    fun observe(): Flow<List<PostEntity>> = updatesState

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

    private fun updatePosts() {
        scope.launch {
            updatesState.value = updatesState.value.mapIndexed { index, post ->
                post.copy(
                    likes = (post.likes + randomLike()).coerceAtLeast(0),
                    comments = (post.comments + randomComment()).coerceAtLeast(0),
                    saves = (post.saves + randomSaves()).coerceAtLeast(0),
                    views = (post.views + randomViews()).coerceAtLeast(0),
                    comment = post.comment?.let { comment ->
                        comment.copy(
                            likes = (comment.likes + randomSaves()).coerceAtLeast(0)
                        )
                    }
                )
            }
        }
    }

    private fun randomLike() = (-5..150).random()
    private fun randomComment() = (-1..3).random()
    private fun randomSaves() = (-1..2).random()
    private fun randomViews() = (0..100).random()
}
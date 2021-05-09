package ru.radiationx.kdiffersample.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.radiationx.kdiffersample.data.entity.CommentEntity
import ru.radiationx.kdiffersample.data.entity.CommentUpdateEntity
import ru.radiationx.kdiffersample.data.entity.PostUpdateEntity
import kotlin.concurrent.fixedRateTimer

class UpdatesDataSource {


    val scope = CoroutineScope(Dispatchers.IO)

    private val updates = (1 until 25).map {
        PostUpdateEntity(
            postId = "post_$it",
            likes = 0,
            comments = 0,
            saves = 0,
            views = 0,
            liked = false,
            commented = false,
            saved = false
        )
    }

    private val updatesState = MutableStateFlow(updates)

    init {
        val timer = fixedRateTimer(period = (1000 / 60f).toLong()) {
            updatePosts()
        }
    }

    fun observePosts(): Flow<List<PostUpdateEntity>> {
        return updatesState

    }

    fun observeComments(): Flow<CommentUpdateEntity> {
        return flow<CommentUpdateEntity> { }
    }

    fun observeNewComments(): Flow<CommentEntity> {
        return flow<CommentEntity> { }
    }


    private fun updatePosts() {
        scope.launch {
            updatesState.value = updatesState.value.mapIndexed { index, post ->
                post.copy(
                    likes = (post.likes + randomLike()).coerceAtLeast(0),
                    comments = (post.comments + randomComment()).coerceAtLeast(0),
                    saves = (post.saves + randomSaves()).coerceAtLeast(0),
                    views = (post.views + randomViews()).coerceAtLeast(0),
                )
            }
        }
    }

    private fun randomLike() = (-5..150).random()
    private fun randomComment() = (-1..3).random()
    private fun randomSaves() = (-1..2).random()
    private fun randomViews() = (0..100).random()
}
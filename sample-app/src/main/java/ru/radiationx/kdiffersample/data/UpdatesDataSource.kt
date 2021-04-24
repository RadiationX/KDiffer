package ru.radiationx.kdiffersample.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import ru.radiationx.kdiffersample.data.entity.CommentEntity
import ru.radiationx.kdiffersample.data.entity.CommentUpdateEntity
import ru.radiationx.kdiffersample.data.entity.PostUpdateEntity
import kotlin.concurrent.fixedRateTimer

class UpdatesDataSource {

    private val updates = (1 until 5).map {
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
        val timer = fixedRateTimer(period = 500) {
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
        Log.d("kekeke", "updatePosts")
        updatesState.value = updatesState.value.map {
            it.copy(
                likes = it.likes + randomLike(),
                comments = it.comments + randomComment(),
                saves = it.saves + randomSaves(),
                views = it.views + randomViews()
            )
        }
    }

    private fun randomLike() = (-10..30).random()
    private fun randomComment() = (-1..5).random()
    private fun randomSaves() = (-1..3).random()
    private fun randomViews() = (0..100).random()
}
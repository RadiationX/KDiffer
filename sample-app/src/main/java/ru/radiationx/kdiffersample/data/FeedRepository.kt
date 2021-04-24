package ru.radiationx.kdiffersample.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import ru.radiationx.kdiffersample.data.entity.CommentUpdateEntity
import ru.radiationx.kdiffersample.data.entity.PostEntity
import ru.radiationx.kdiffersample.data.entity.PostUpdateEntity

class FeedRepository {

    val feedDataSource = FeedDataSource()
    val updatesDataSource = UpdatesDataSource()

    fun observe(): Flow<List<PostEntity>> {
        return flow<List<PostEntity>> {
            emit(feedDataSource.getPosts())
        }
            .combine(updatesDataSource.observePosts()) { posts: List<PostEntity>, updates: List<PostUpdateEntity> ->
                Log.d("kekeke", "combine posts for ${updates.map { it.postId }}")
                posts.map { post ->
                    val update = updates.firstOrNull { post.id == it.postId }
                    if (post.id == update?.postId) {
                        updatePost(post, update)
                    } else {
                        post
                    }
                }
            }
    }

    /*fun observe1(): Flow<List<PostEntity>> {
        return flow<List<PostEntity>> {
            emit(feedDataSource.getPosts())
        }
            .combine(updatesDataSource.observePosts()) { posts: List<PostEntity>, update: PostUpdateEntity ->
                Log.d("kekeke", "combine posts")
                posts.map {
                    if (it.id == update.postId) {
                        updatePost(it, update)
                    } else {
                        it
                    }
                }
            }
            .combine(updatesDataSource.observeNewComments()) { posts: List<PostEntity>, comment: CommentEntity ->
                Log.d("kekeke", "combine new comments")
                posts.map {
                    if (it.id == comment.postId) {
                        it.copy(comment = comment)
                    } else {
                        it
                    }
                }
            }
            .combine(updatesDataSource.observeComments()) { posts: List<PostEntity>, update: CommentUpdateEntity ->
                Log.d("kekeke", "combine comments")
                posts.map {
                    if (it.id == update.postId) {
                        updateComment(it, update)
                    } else {
                        it
                    }
                }
            }
    }*/

    private fun updateComment(postEntity: PostEntity, update: CommentUpdateEntity): PostEntity {
        return postEntity.copy(
            comment = postEntity.comment?.copy(
                likes = update.likes,
                liked = update.liked
            )
        )
    }

    private fun updatePost(postEntity: PostEntity, update: PostUpdateEntity): PostEntity {
        return postEntity.copy(
            likes = update.likes,
            comments = update.comments,
            saves = update.saves,
            views = update.views,
            liked = update.liked,
            commented = update.commented,
            saved = update.saved,
        )
    }
}
package ru.radiationx.kdiffersample

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import ru.radiationx.kdiffersample.data.FeedRepository
import ru.radiationx.kdiffersample.data.entity.PostEntity

class MainViewModel(
    private val repository: FeedRepository
) : ViewModel() {

    private val _postsItemsState = MutableStateFlow(emptyList<PostItemState>())
    val postsItemsState: StateFlow<List<PostItemState>> = _postsItemsState

    init {
        observePosts()
    }

    private fun observePosts() {
        repository
            .observe()
            .map {
                it.map { post -> post.toState() }
            }
            .onEach {
                _postsItemsState.value = it
            }
            .launchIn(viewModelScope + Dispatchers.IO)
    }

    private fun PostEntity.toState() = PostItemState(
        id,
        PostItemHeaderState(
            authorName,
            authorAvatarUrl,
            groupName,
            date
        ),
        PostItemContentState(
            contentText,
            contentImage
        ),
        PostItemFooterState(
            likes,
            comments,
            saves,
            views,
            liked,
            commented,
            saved
        ),
        comment?.run {
            PostItemCommentState(
                id,
                authorName,
                commentText,
                likes,
                liked
            )
        }
    )
}
package ru.radiationx.kdiffersample

import android.text.format.DateUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import ru.radiationx.kdiffersample.data.FeedRepository
import ru.radiationx.kdiffersample.data.entity.PostEntity
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import java.util.*
import java.util.concurrent.TimeUnit

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
            date.humanReadable()
        ),
        PostItemContentState(
            contentText,
            contentImage
        ),
        PostItemFooterState(
            likes.humanReadable(),
            comments.humanReadable(),
            saves.humanReadable(),
            views.humanReadable(),
            liked,
            commented,
            saved
        ),
        comment?.run {
            PostItemCommentState(
                id,
                authorName,
                commentText,
                likes.humanReadable(),
                liked
            )
        }
    )

    private fun Date.humanReadable(): String {
        return time.toString()
    }

    private fun Int.humanReadable(): String {
        return humanReadableReal()
    }

    private fun Date.humanReadableFake(): String {
        val delta = (System.currentTimeMillis() - this.time)
        return TimeUnit.MILLISECONDS.toSeconds(delta).toString()
    }

    private fun Int.humanReadableFake(): String {
        return this.toString()
    }

    private fun Date.humanReadableReal(): String {
        val now = System.currentTimeMillis()
        return DateUtils.getRelativeTimeSpanString(this.time, now, DateUtils.SECOND_IN_MILLIS)
            .toString()
    }

    private fun Int.humanReadableReal(): String {
        var bytes = this
        if (-1000 < bytes && bytes < 1000) {
            return "$bytes"
        }
        val ci: CharacterIterator = StringCharacterIterator("KMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }

        return "%.1f %c".format(bytes / 1000.0, ci.current())
    }
}
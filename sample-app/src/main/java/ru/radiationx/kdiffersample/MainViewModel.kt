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

    private val _postsItemsState = MutableStateFlow(emptyList<PostEntity>())
    val postsItemsState: StateFlow<List<PostEntity>> = _postsItemsState

    init {
        observePosts()
    }

    private fun observePosts() {
        repository
            .observe()
            .onEach {
                _postsItemsState.value = it
            }
            .launchIn(viewModelScope + Dispatchers.IO)
    }
}
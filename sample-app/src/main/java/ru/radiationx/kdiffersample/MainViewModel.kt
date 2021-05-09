package ru.radiationx.kdiffersample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import ru.radiationx.kdiffersample.data.FeedRepository
import ru.radiationx.kdiffersample.data.entity.PostEntity

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
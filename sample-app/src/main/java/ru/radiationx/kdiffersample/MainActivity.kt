package ru.radiationx.kdiffersample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.radiationx.kdiffersample.data.FeedRepository
import ru.radiationx.kdiffersample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding<ActivityMainBinding>()
    private val postAdapter by lazy { PostAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel(FeedRepository())

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
        }

        viewModel.postsItemsState.onEach {
            Log.d("kekeke", "postsItemsState $it")
            postAdapter.submitList(it)
        }.launchIn(lifecycleScope)
    }

}
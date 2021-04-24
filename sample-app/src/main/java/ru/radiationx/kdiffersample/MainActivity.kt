package ru.radiationx.kdiffersample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.radiationx.kdiffersample.data.FeedRepository
import ru.radiationx.kdiffersample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding<ActivityMainBinding>()
    //private val postAdapter by lazy { DefaultPostAdapter() }
    private val postAdapter by lazy { DifferPostAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel(FeedRepository())

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(PostDecorator())
            adapter = postAdapter
            Log.d("kekeke", "itemanimator ${itemAnimator}")
            (itemAnimator as? DefaultItemAnimator)?.apply {
                supportsChangeAnimations = false
            }
            //itemAnimator = null
        }

        viewModel.postsItemsState.onEach {
            postAdapter.submitList(it)
        }.launchIn(lifecycleScope)
    }

}
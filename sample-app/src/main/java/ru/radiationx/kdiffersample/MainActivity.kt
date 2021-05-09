package ru.radiationx.kdiffersample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.radiationx.kdiffersample.data.FeedRepository
import ru.radiationx.kdiffersample.data.entity.PostEntity
import ru.radiationx.kdiffersample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val repository = FeedRepository()
    private val viewModel = MainViewModel(repository)
    private val binding by viewBinding<ActivityMainBinding>()
    private var activeDiffer = false

    private val defaultPostAdapter = DefaultPostAdapter()
    private val differPostAdapter = DifferPostAdapter()
    private var postAdapter = getActualAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateTitle()


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

    override fun onStart() {
        super.onStart()
        repository.initTimer()
    }

    override fun onStop() {
        super.onStop()
        repository.cancelTimer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("change")
            .setOnMenuItemClickListener {
                activeDiffer = !activeDiffer
                updateAdapter()
                updateTitle()
                return@setOnMenuItemClickListener true
            }
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    private fun updateAdapter() {
        postAdapter = getActualAdapter()
        binding.recyclerView.adapter = postAdapter
    }

    private fun updateTitle() {
        supportActionBar?.subtitle = if (activeDiffer) {
            "Active: differ"
        } else {
            "Active: default"
        }
    }

    private fun getActualAdapter(): ListAdapter<PostEntity, *> = if (activeDiffer) {
        differPostAdapter
    } else {
        defaultPostAdapter
    }
}
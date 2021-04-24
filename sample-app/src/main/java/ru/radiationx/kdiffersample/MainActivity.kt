package ru.radiationx.kdiffersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.radiationx.kdiffersample.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding<ActivityMainBinding>()
    private val postAdapter by lazy { PostAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
        }

        postAdapter.submitList(
            listOf(
                PostItemState(
                    "psot_1",
                    PostItemHeaderState(
                        "Author Name",
                        "Group Name",
                        Date()
                    ),
                    PostItemContentState(
                        "ContentText",
                        null
                    ),
                    PostItemFooterState(
                        100,
                        200,
                        10,
                        10000,
                        false,
                        false,
                        false
                    ),
                    PostItemCommentState(
                        "comment_1",
                        "Author Name",
                        "Awesome Comment",
                        22,
                        false
                    )
                )
            )
        )
    }

}
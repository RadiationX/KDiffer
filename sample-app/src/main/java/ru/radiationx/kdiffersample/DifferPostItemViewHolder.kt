package ru.radiationx.kdiffersample

import android.util.Log
import androidx.core.view.isVisible
import androidx.transition.AutoTransition
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import ru.radiationx.kdiffer.dsl.ext.call
import ru.radiationx.kdiffer.dsl.ext.registerMutableDiffer
import ru.radiationx.kdiffer.dsl.ext.value
import ru.radiationx.kdiffer.mutableLiveDiffer
import ru.radiationx.kdiffersample.databinding.ItemPostBinding

class DifferPostItemViewHolder(
    private val binding: ItemPostBinding
) : PostItemViewHolder(binding) {

    private val headerDiffer = mutableLiveDiffer<PostItemHeaderState> {
        val headerBinding = binding.postHeader
        value { it.groupName } call {
            headerBinding.postGroupName.text = it
        }
        value { it.authorName } call {
            headerBinding.postAuthorName.text = it
        }
        value { it.date } call {
            headerBinding.postDate.text = it.time.toString()
        }
    }

    private val contentDiffer = mutableLiveDiffer<PostItemContentState> {
        val contentBinding = binding.postContent
        value { it.contentText } call {
            contentBinding.postContentText.text = it
        }
        value { it.contentText != null } call {
            contentBinding.postContentText.isVisible = it
        }
        value { it.contentImage != null } call {
            contentBinding.postContentImage.isVisible = it
        }
    }

    private val footerDiffer = mutableLiveDiffer<PostItemFooterState> {
        val footerBinding = binding.postFooter
        /*value { it.length() } call {
            TransitionManager.beginDelayedTransition(footerBinding.root, AutoTransition())
        }*/
        value { it.likes } call {
            footerBinding.postFooterLikes.text = it.toString()
        }
        value { it.comments } call {
            footerBinding.postFooterComments.text = it.toString()
        }
        value { it.saves } call {
            footerBinding.postFooterSaves.text = it.toString()
        }
        value { it.views } call {
            footerBinding.postFooterViews.text = it.toString()
        }
    }

    fun PostItemFooterState.length(): Int = listOf(likes, comments, saves, views)
        .map { it.toString().length }
        .sum()

    private val commentDiffer = mutableLiveDiffer<PostItemCommentState?> {
        val commentBinding = binding.postFooterComment
        value { it != null } call {
            commentBinding.root.isVisible = it
        }
        value { it?.authorName } call {
            commentBinding.commentAuthorName.text = it
        }
        value { it?.commentText } call {
            commentBinding.commentText.text = it
        }
        value { it?.likes } call {
            commentBinding.commentLikes.text = (it ?: 0).toString()
        }
    }

    private val postDiffer = mutableLiveDiffer<PostItemState> {
        value { it.header }.registerMutableDiffer(headerDiffer)
        value { it.content }.registerMutableDiffer(contentDiffer)
        value { it.footer }.registerMutableDiffer(footerDiffer)
        value { it.comment }.registerMutableDiffer(commentDiffer)
    }

    init {
        Log.d("kekeke", "init vh $this")
    }

    override fun bind(item: PostItemState, payloads: MutableList<Any>?) {
        postDiffer.accept(item)
    }
}
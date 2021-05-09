package ru.radiationx.kdiffersample

import androidx.core.view.isVisible
import ru.radiationx.kdiffer.dsl.ext.call
import ru.radiationx.kdiffer.dsl.ext.registerMutableDiffer
import ru.radiationx.kdiffer.dsl.ext.value
import ru.radiationx.kdiffer.mutableLiveDiffer
import ru.radiationx.kdiffersample.data.entity.CommentEntity
import ru.radiationx.kdiffersample.data.entity.PostEntity
import ru.radiationx.kdiffersample.databinding.ItemPostBinding

class DifferPostItemViewHolder(
    private val binding: ItemPostBinding
) : PostItemViewHolder(binding) {

    private val headerDiffer = with(binding.postHeader) {
        mutableLiveDiffer<PostEntity> {
            value { it.groupName } call {
                postGroupName.text = it
            }
            value { it.authorName } call {
                postAuthorName.text = it
            }
            value { it.date } call {
                postDate.text = it.formatDate()
            }
        }
    }

    private val contentDiffer = with(binding.postContent) {
        mutableLiveDiffer<PostEntity> {
            value { it.contentText } call {
                postContentText.text = it?.formatHtml()
            }
            value { it.contentText != null } call {
                postContentText.isVisible = it
            }
            value { it.contentImage != null } call {
                postContentImage.isVisible = it
            }
        }
    }

    private val footerDiffer = with(binding.postFooter) {
        mutableLiveDiffer<PostEntity> {
            value { it.likes } call {
                postFooterLikes.text = it.formatCounter()
            }
            value { it.comments } call {
                postFooterComments.text = it.formatCounter()
            }
            value { it.saves } call {
                postFooterSaves.text = it.formatCounter()
            }
            value { it.views } call {
                postFooterViews.text = it.formatCounter()
            }
        }
    }

    private val commentDiffer = with(binding.postFooterComment) {
        mutableLiveDiffer<CommentEntity?> {
            value { it != null } call {
                root.isVisible = it
            }
            value { it?.authorName } call {
                commentAuthorName.text = it
            }
            value { it?.commentText } call {
                commentText.text = it?.formatHtml()
            }
            value { it?.likes } call {
                commentLikes.text = (it ?: 0).formatCounter()
            }
        }
    }

    private val postDiffer = mutableLiveDiffer<PostEntity> {
        value { it }.registerMutableDiffer(headerDiffer)
        value { it }.registerMutableDiffer(contentDiffer)
        value { it }.registerMutableDiffer(footerDiffer)
        value { it.comment }.registerMutableDiffer(commentDiffer)
    }

    override fun bind(item: PostEntity, payloads: MutableList<Any>?) {
        postDiffer.accept(item)
    }
}
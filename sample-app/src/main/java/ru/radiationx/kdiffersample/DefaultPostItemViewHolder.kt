package ru.radiationx.kdiffersample

import androidx.core.view.isVisible
import ru.radiationx.kdiffersample.adapter.PostItemViewHolder
import ru.radiationx.kdiffersample.data.entity.PostEntity
import ru.radiationx.kdiffersample.databinding.ItemPostBinding

class DefaultPostItemViewHolder(
    private val binding: ItemPostBinding
) : PostItemViewHolder(binding) {

    override fun bind(item: PostEntity, payloads: MutableList<Any>?) {

        with(binding.postHeader) {
            postGroupName.text = item.groupName
            postAuthorName.text = item.authorName
            postDate.text = item.date.formatDate()
        }

        with(binding.postContent) {
            postContentText.text = item.contentText?.formatHtml()
            postContentText.isVisible = item.contentText != null
            postContentImage.isVisible = item.contentImage != null
        }

        with(binding.postFooter) {
            postFooterLikes.text = item.likes.formatCounter()
            postFooterComments.text = item.comments.formatCounter()
            postFooterSaves.text = item.saves.formatCounter()
            postFooterViews.text = item.views.formatCounter()
        }

        with(binding.postFooterComment) {
            val comment = item.comment
            root.isVisible = comment != null
            commentAuthorName.text = comment?.authorName
            commentText.text = comment?.commentText?.formatHtml()
            commentLikes.text = comment?.likes?.formatCounter()
        }
    }
}
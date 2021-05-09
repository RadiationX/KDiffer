package ru.radiationx.kdiffersample

import android.text.Html
import androidx.core.view.isVisible
import ru.radiationx.kdiffersample.databinding.ItemPostBinding

class DefaultPostItemViewHolder(
    private val binding: ItemPostBinding
) : PostItemViewHolder(binding) {

    override fun bind(item: PostItemState, payloads: MutableList<Any>?) {
        val header = item.header
        val content = item.content
        val footer = item.footer
        val comment = item.comment

        with(binding.postHeader) {
            postGroupName.text = header.groupName
            postAuthorName.text = header.authorName
            postDate.text = header.date
        }

        with(binding.postContent) {
            postContentText.text = content.contentText?.let { Html.fromHtml(it) }
            postContentText.isVisible = content.contentText != null
            postContentImage.isVisible = content.contentImage != null
        }

        with(binding.postFooter) {
            postFooterLikes.text = footer.likes
            postFooterComments.text = footer.comments
            postFooterSaves.text = footer.saves
            postFooterViews.text = footer.views
        }

        with(binding.postFooterComment) {
            root.isVisible = comment != null
            commentAuthorName.text = comment?.authorName
            commentText.text = comment?.commentText?.let { Html.fromHtml(it) }
            commentLikes.text = comment?.likes
        }
    }
}
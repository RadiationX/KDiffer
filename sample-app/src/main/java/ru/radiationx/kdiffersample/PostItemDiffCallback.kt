package ru.radiationx.kdiffersample

import android.util.Log
import androidx.recyclerview.widget.DiffUtil

object PostItemDiffCallback : DiffUtil.ItemCallback<PostItemState>() {

    override fun areItemsTheSame(oldItem: PostItemState, newItem: PostItemState): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostItemState, newItem: PostItemState): Boolean {
        return oldItem == newItem
    }

    /*override fun getChangePayload(oldItem: PostItemState, newItem: PostItemState): Any? {
        return Unit
    }*/
}
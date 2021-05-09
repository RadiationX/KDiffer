package ru.radiationx.kdiffersample.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import ru.radiationx.kdiffersample.data.entity.PostEntity

object PostItemDiffCallback : DiffUtil.ItemCallback<PostEntity>() {

    override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem == newItem
    }

    /*override fun getChangePayload(oldItem: PostItemState, newItem: PostItemState): Any? {
        return Unit
    }*/
}
package ru.radiationx.kdiffersample

import androidx.recyclerview.widget.RecyclerView
import ru.radiationx.kdiffersample.data.entity.PostEntity
import ru.radiationx.kdiffersample.databinding.ItemPostBinding

abstract class PostItemViewHolder(
    binding: ItemPostBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: PostEntity, payloads: MutableList<Any>?)
}
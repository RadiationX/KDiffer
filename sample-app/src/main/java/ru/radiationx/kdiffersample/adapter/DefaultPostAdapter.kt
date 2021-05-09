package ru.radiationx.kdiffersample.adapter

import ru.radiationx.kdiffersample.databinding.ItemPostBinding

class DefaultPostAdapter : PostAdapter<DefaultPostItemViewHolder>() {

    override fun createViewHolder(binding: ItemPostBinding): DefaultPostItemViewHolder {
        return DefaultPostItemViewHolder(binding)
    }
}
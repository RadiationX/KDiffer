package ru.radiationx.kdiffersample

import ru.radiationx.kdiffersample.databinding.ItemPostBinding

class DifferPostAdapter : PostAdapter<DifferPostItemViewHolder>() {

    override fun createViewHolder(binding: ItemPostBinding): DifferPostItemViewHolder {
        return DifferPostItemViewHolder(binding)
    }
}
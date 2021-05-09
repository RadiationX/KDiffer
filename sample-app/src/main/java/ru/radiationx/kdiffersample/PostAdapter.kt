package ru.radiationx.kdiffersample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.radiationx.kdiffersample.data.entity.PostEntity
import ru.radiationx.kdiffersample.databinding.ItemPostBinding

abstract class PostAdapter<VH : PostItemViewHolder> :
    ListAdapter<PostEntity, VH>(PostItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return createViewHolder(binding)
    }

    abstract fun createViewHolder(binding: ItemPostBinding): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), null)
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        holder.bind(getItem(position), payloads)
    }
}
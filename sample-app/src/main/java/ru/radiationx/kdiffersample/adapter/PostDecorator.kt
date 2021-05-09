package ru.radiationx.kdiffersample.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PostDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val dpi = parent.context.resources.displayMetrics.density
        outRect.top = (8 * dpi).toInt()
        outRect.bottom = (8 * dpi).toInt()
    }
}
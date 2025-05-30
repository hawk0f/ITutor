package dev.hawk0f.itutor.core.presentation.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseHorizontalDividerItemDecoration(private val divider: Int) : RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val oneSideHorizontalDivider = divider / 2

        with(outRect) {
            left = oneSideHorizontalDivider
            right = oneSideHorizontalDivider
        }
    }
}
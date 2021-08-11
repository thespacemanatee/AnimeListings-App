package com.example.animelistings.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorations {
    class VerticalSpacing(private val value: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = value / 2
            outRect.left = value / 2
            if (parent.getChildAdapterPosition(view) < (parent.adapter?.itemCount ?: 0) - 2) {
                outRect.bottom = value;
            }
        }
    }
}
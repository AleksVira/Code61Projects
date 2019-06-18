package ru.virarnd.recipeskt.ui.main

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.recipeskt.common.toPx

class RecipeListDecorator(private val offsetInDp: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val halfInPx = (offsetInDp / 2).toPx
        val fullInPx = offsetInDp.toPx

        outRect.bottom = halfInPx;

        // Поля сверху будут нулевые для первого ряда
        // Поля слева/справа будут зависеть от колонки (левая/правая)
        when (itemPosition) {
            0, 1 -> outRect.top = 0;
            else -> outRect.top = halfInPx
        }

        when (itemPosition % 2) {
            0 -> {
                outRect.left = fullInPx;
                outRect.right = halfInPx;
            }
            else -> {
                outRect.left = halfInPx;
                outRect.right = fullInPx;
            }
        }
    }
}

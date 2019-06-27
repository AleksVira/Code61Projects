package ru.virarnd.userslistrestapicode61.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.userslistrestapicode61.common.toPx

class RecipeListDecorator(private val offsetInDp: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val halfInPx = (offsetInDp / 2).toPx
        val fullInPx = offsetInDp.toPx

        outRect.bottom = halfInPx
        outRect.top = halfInPx
        // Поля слева/справа будут зависеть от четности
        when (itemPosition % 2) {
            0 -> {
                outRect.left = fullInPx * 2
                outRect.right = halfInPx
            }
            else -> {
                outRect.left = halfInPx
                outRect.right = fullInPx * 2
            }
        }
    }
}

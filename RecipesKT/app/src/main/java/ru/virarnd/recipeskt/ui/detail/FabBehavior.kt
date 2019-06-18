package ru.virarnd .recipeskt.ui.detail

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.INVISIBLE
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import timber.log.Timber

open class FabBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        Timber.d("onStartNestedScroll, ${axes == ViewCompat.SCROLL_AXIS_VERTICAL}")
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }


    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        Timber.d("onNestedScroll, ${dyConsumed}, ${dyUnconsumed}, target = ${target}")
        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            child.hide(object: FloatingActionButton.OnVisibilityChangedListener() {
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
                    (fab as View).visibility = INVISIBLE
                }
            })
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE)
            child.show()
    }
}
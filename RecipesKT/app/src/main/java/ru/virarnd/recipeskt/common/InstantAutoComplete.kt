package ru.virarnd.recipeskt.common

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.AutoCompleteTextView
import com.github.ajalt.timberkt.Timber

class InstantAutoComplete : AutoCompleteTextView {
    constructor(arg0: Context, arg1: AttributeSet, arg2: Int) : super(arg0, arg1, arg2) {}
    constructor(arg0: Context, arg1: AttributeSet) : super(arg0, arg1) {}
    constructor(arg0: Context) : super(arg0) {}


    override fun enoughToFilter(): Boolean {
        return true
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused && filter != null) {
            performFiltering(text, 0);
        }
    }
}
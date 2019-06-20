package ru.virarnd.recipeskt.common

import android.content.res.Resources

const val MAIN_FRAGMENT_TAG: String = "MainFragment"
const val DETAIL_FRAGMENT_TAG: String = "DetailFragment"
const val EDIT_FRAGMENT_TAG: String = "EditFragment"


val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
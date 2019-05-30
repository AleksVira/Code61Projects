package ru.virarnd.recipeskt.ui.main

import androidx.recyclerview.widget.DiffUtil
import ru.virarnd.recipeskt.data.Recipe

class RecipeDiffUtilCallback(private val oldList: List<Recipe>, private val newList: List<Recipe>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].name == newList[newItemPosition].name

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
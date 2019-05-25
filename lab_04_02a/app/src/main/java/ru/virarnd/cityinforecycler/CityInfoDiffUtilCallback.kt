package ru.virarnd.cityinforecycler

import androidx.recyclerview.widget.DiffUtil

class CityInfoDiffUtilCallback(private val oldList: List<CityInfo>, private val newList: List<CityInfo>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
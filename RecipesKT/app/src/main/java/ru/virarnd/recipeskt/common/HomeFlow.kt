package ru.virarnd.recipeskt.common

interface Flow

interface HomeFlow : Flow {
    fun openMainList()
    fun openDetails(position: Int)
    fun openEdit(position: Int)
    fun changeTitle(title: String?)
    fun changeHomeAsUpEnabled(visible: Boolean)
}

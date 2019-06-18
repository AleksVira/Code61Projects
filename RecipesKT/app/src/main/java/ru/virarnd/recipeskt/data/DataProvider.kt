package ru.virarnd.recipeskt.data

interface DataProvider<Data> {

    fun provide(): Data

//    fun getByName(name: String): Recipe

    fun isPresent(recipe: Recipe): Boolean

    fun addNewEmptyRecipe()

    fun updateRecipe(position: Int, newRecipe: PendingRecipe)

    //TODO Добавить метод для удаления
    // Возможно, будет реагировать на свайп на элементе
}
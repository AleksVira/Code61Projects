package ru.virarnd.recipeskt.data

interface DataProvider<Data> {

    fun provide(): Data

//    fun getByName(name: String): Recipe

    fun isPresent(recipe: Recipe): Boolean

    fun addRecipe(recipe: Recipe)

    fun updateRecipe(recipe: Recipe)

    //TODO Добавить метод для удаления
    // Возможно, будет реагировать на свайп на элементе
}
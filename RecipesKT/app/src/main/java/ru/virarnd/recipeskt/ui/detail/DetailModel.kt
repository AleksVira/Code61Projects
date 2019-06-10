package ru.virarnd.recipeskt.ui.detail

import ru.virarnd.recipeskt.data.RecipeDataProvider

class DetailModel(val position: Int) {
    val currentRecipe = RecipeDataProvider.getRecipe(position)


}
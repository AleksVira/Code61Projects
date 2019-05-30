package ru.virarnd.recipeskt.ui.main

import androidx.lifecycle.ViewModel
import ru.virarnd.recipeskt.data.DataProvider
import ru.virarnd.recipeskt.data.Recipe
import ru.virarnd.recipeskt.data.RecipeDataProvider

class MainViewModel : ViewModel() {
    val recipesData: DataProvider<List<Recipe>> = RecipeDataProvider



}
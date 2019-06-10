package ru.virarnd.recipeskt.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.virarnd.recipeskt.R
import ru.virarnd.recipeskt.data.Recipe
import ru.virarnd.recipeskt.data.RecipeDataProvider
import ru.virarnd.recipeskt.ui.detail.DetailActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var myAdapter: RecipesRVAdapter
    lateinit var recipes : List<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recipes = RecipeDataProvider.provide()
        myAdapter = RecipesRVAdapter { position: Int -> recipeClicked(position) }
        rv_recipes.adapter = myAdapter
        myAdapter.updateRecipes(recipes)

    }

    private fun recipeClicked(position: Int) {
        val intent = DetailActivity.newIntent(this, position)
        Timber.d("position in mainActivity = ${position}")
        startActivity(intent)
    }
}

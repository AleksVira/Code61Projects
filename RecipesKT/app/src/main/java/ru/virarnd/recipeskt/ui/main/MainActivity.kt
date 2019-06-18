package ru.virarnd.recipeskt.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.activity_main.*
import ru.virarnd.recipeskt.R
import ru.virarnd.recipeskt.data.PendingRecipe
import ru.virarnd.recipeskt.data.RecipeDataProvider
import ru.virarnd.recipeskt.ui.detail.DetailActivity
import ru.virarnd.recipeskt.ui.edit.EditActivity

class MainActivity : AppCompatActivity() {

    private lateinit var myAdapter: RecipesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        myAdapter = RecipesRVAdapter { position: Int -> recipeClicked(position) }
        rv_recipes.addItemDecoration(RecipeListDecorator(8))
        rv_recipes.adapter = myAdapter
        myAdapter.updateRecipes(RecipeDataProvider.getRecipesList())
        fab_main.setOnClickListener { addNewRecipe() }
    }

    private fun addNewRecipe() {
        val intent = EditActivity.newIntent(this, null)
        startActivity(intent)
    }

    private fun recipeClicked(position: Int) {
        val intent = DetailActivity.newIntent(this, position)
        Timber.d { "position in mainActivity = ${position}" }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        myAdapter.updateRecipes(RecipeDataProvider.getRecipesList())
    }

}

package ru.virarnd.recipeskt.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.virarnd.recipeskt.R
import ru.virarnd.recipeskt.data.RecipeDataProvider

class MainActivity : AppCompatActivity() {

//    lateinit var viewModel: MainViewModel
    lateinit var myAdapter: RecipesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val dataProvider = RecipeDataProvider
        val recipes = dataProvider.provide()
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        myAdapter = RecipesRVAdapter { position: Int -> recipeClicked(position) }
        rv_recipes.adapter = myAdapter
        myAdapter.updateRecipes(recipes)

    }

    private fun recipeClicked(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

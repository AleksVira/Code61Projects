package ru.virarnd.recipeskt.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_recipe.view.*
import ru.virarnd.recipeskt.R
import ru.virarnd.recipeskt.data.Recipe

class RecipesRVAdapter(val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<RecipesRVAdapter.ViewHolder>() {

    private var mainRecipesList = emptyList<Recipe>()
    private val newRecipesList = listOf<Recipe>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = mainRecipesList.size

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(position: Int) = with(containerView) {
            val recipe = mainRecipesList[position]

            image.setImageURI(recipe.images[0]);
            tv_card_recipe_name.text = recipe.name
            val timeString = "${recipe.preparationTimeInMin} min"
            tv_card_cooking_time.text = timeString
            setOnClickListener {
                onItemClick(position)
            }
        }
    }

    fun updateRecipes(recipes: List<Recipe>) {
        val recipeDiffUtilCallback = RecipeDiffUtilCallback(mainRecipesList, recipes)
        val recipesDiffResult = DiffUtil.calculateDiff(recipeDiffUtilCallback, false)
        recipesDiffResult.dispatchUpdatesTo(this)
        //TODO Сохранить новый список рецептов в DataProvider (не здесь? До вызова этой функции?)
        mainRecipesList = recipes

    }


}

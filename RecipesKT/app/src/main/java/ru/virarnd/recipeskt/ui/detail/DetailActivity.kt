package ru.virarnd.recipeskt.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import com.github.ajalt.timberkt.Timber
import ru.virarnd.recipeskt.R

import kotlinx.android.synthetic.main.activity_detail.*
import ru.virarnd.recipeskt.data.NutritionFact
import ru.virarnd.recipeskt.data.RecipeDataProvider
import ru.virarnd.recipeskt.ui.edit.EditActivity

class DetailActivity : AppCompatActivity() {

    var _position: Int = 0

    companion object {
        private const val INTENT_POSITION_LABEL = "recipe_position"
        private const val REQUEST_EDIT_CODE = 152

        fun newIntent(context: Context, position: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_POSITION_LABEL, position)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Recipe description"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        savedInstanceState?.let {
            _position = savedInstanceState.getInt(INTENT_POSITION_LABEL)
            Timber.d { "Position from savedInstanceState = ${_position}" }
        } ?: run {
            _position = intent.getIntExtra(INTENT_POSITION_LABEL, -1)
            Timber.d { "Position from intent = ${_position}" }
        }
        init(_position)
    }

    private fun init(position: Int) {
        val recipe = RecipeDataProvider.getRecipe(position)
        view_pager.adapter = DetailPagerAdapter(recipe)

        val detailAdapter = IngredientsAdapter(recipe.ingredients)
        rv_ingredients.adapter = detailAdapter


        with(recipe) {
            detail_header.text = name
            val personStr = "${persons} person"
            tv_person_count.text = personStr
            val minutesStr = "${preparationTimeInMin} min"
            tv_detail_minutes.text = minutesStr
            tv_detail_description.text = description

            // Если содержимое какого-то компонента неизвестно, убираю соответствующие элементы.
            val nutritionFactCalories = nutritionFacts.find { it.type == NutritionFact.Type.CALORIES }
            if (nutritionFactCalories != null) {
                tv_calories_quantity.text = nutritionFactCalories.value.toString()
            } else {
                makeViewsInvisible(
                    listOf(
                        tv_calories_label,
                        divider_1a,
                        tv_calories_quantity,
                        divider_2a
                    )
                )
            }
            val nutritionFactProtein = nutritionFacts.find { it.type == NutritionFact.Type.PROTEIN }
            if (nutritionFactProtein != null) {
                tv_protein_quantity.text = nutritionFactProtein.value.toString()
            } else {
                makeViewsInvisible(
                    listOf(
                        tv_protein_label,
                        divider_1b,
                        tv_protein_quantity,
                        divider_2b
                    )
                )
            }
            val nutritionFactFat = nutritionFacts.find { it.type == NutritionFact.Type.FAT }
            if (nutritionFactFat != null) {
                tv_fat_quantity.text = nutritionFactFat.value.toString()
            } else {
                makeViewsInvisible(
                    listOf(
                        tv_fat_label,
                        divider_1c,
                        tv_fat_quantity,
                        divider_2c
                    )
                )
            }
            val nutritionFactCarbs = nutritionFacts.find { it.type == NutritionFact.Type.CARBS }
            if (nutritionFactCarbs != null) {
                tv_carbs_quantity.text = nutritionFactCarbs.value.toString()
            } else {
                makeViewsInvisible(
                    listOf(
                        tv_carbs_label,
                        divider_1c,
                        tv_carbs_quantity,
                        divider_2c
                    )
                )
            }
        }

        fab_detail.setOnClickListener { recipeStartEdit(_position) }
    }

    private fun makeViewsInvisible(listOf: List<View>) = listOf.forEach { it.visibility = View.GONE }


    private fun recipeStartEdit(position: Int) {
        val intent = EditActivity.newIntent(this, position)
        Timber.d { "position in editActivity = ${position}" }
        startActivityForResult(intent, REQUEST_EDIT_CODE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(INTENT_POSITION_LABEL, _position)
    }

    override fun onResume() {
        super.onResume()
        init(_position)
    }


    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
    }
}

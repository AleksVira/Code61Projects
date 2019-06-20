package ru.virarnd.recipeskt.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.fragment_detail.*
import ru.virarnd.recipeskt.R
import ru.virarnd.recipeskt.common.BaseFragment
import ru.virarnd.recipeskt.common.HomeFlow
import ru.virarnd.recipeskt.data.NutritionFact
import ru.virarnd.recipeskt.data.RecipeDataProvider

class DetailFragment : BaseFragment<HomeFlow>() {

    private var position: Int = -1

    companion object {

        fun newInstance(position: Int): DetailFragment {
            val fragment = DetailFragment()
            val arguments = Bundle()
            arguments.putInt("position", position)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            position = arguments!!.getInt("position", -1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d { "DetailFragment -> position = $position" }
        val recipe = RecipeDataProvider.getRecipe(position)
        view_pager.adapter = DetailPagerAdapter(recipe)

        val detailAdapter = IngredientsAdapter(recipe.ingredients)
        rv_ingredients.adapter = detailAdapter


        with(recipe) {
            detail_header.text = name
            val personStr = "$persons person"
            tv_person_count.text = personStr
            val minutesStr = "$preparationTimeInMin min"
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

        fab_detail.setOnClickListener { recipeStartEdit(position) }
    }

    override fun onResume() {
        super.onResume()
        flow?.changeTitle("Recipe description")
        flow?.changeHomeAsUpEnabled(true)
    }

    private fun makeViewsInvisible(listOf: List<View>) = listOf.forEach { it.visibility = View.GONE }


    private fun recipeStartEdit(position: Int) {
        Timber.d { "position in DetailActivity = $position" }
        flow?.openEdit(position)
    }

}
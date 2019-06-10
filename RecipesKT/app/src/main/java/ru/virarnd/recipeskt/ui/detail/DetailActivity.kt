package ru.virarnd.recipeskt.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import ru.virarnd.recipeskt.R

import kotlinx.android.synthetic.main.activity_detail.*
import ru.virarnd.recipeskt.data.NutritionFact
import ru.virarnd.recipeskt.ui.edit.EditActivity
import timber.log.Timber

class DetailActivity : AppCompatActivity() {

    var _position: Int = 0

    companion object{
        private val INTENT_POSITION_LABEL = "recipe_position"
        private val REQUEST_EDIT_CODE = 152

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

        _position = intent.getIntExtra(INTENT_POSITION_LABEL, -1)

        init(_position)
    }

    private fun init(position: Int) {
        val detailModel = DetailModel(position)
        val recipe = detailModel.currentRecipe
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

            tv_calories_quantity.text =
                nutritionFacts.elementAt(nutritionFacts.indexOf(NutritionFact.CALORIES)).value.toString()
            tv_protein_quantity.text =
                nutritionFacts.elementAt(nutritionFacts.indexOf(NutritionFact.PROTEIN)).value.toString()
            tv_fat_quantity.text =
                nutritionFacts.elementAt(nutritionFacts.indexOf(NutritionFact.FAT)).value.toString()
            tv_carbs_quantity.text =
                nutritionFacts.elementAt(nutritionFacts.indexOf(NutritionFact.CARBS)).value.toString()
        }


        fab_detail.setOnClickListener { view ->
            recipeStartEdit(_position)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }



    }

    private fun recipeStartEdit(position: Int) {
        val intent = EditActivity.newIntent(this, position)
        Timber.d("position in editActivity = ${position}")
        startActivityForResult(intent, REQUEST_EDIT_CODE)
    }
}

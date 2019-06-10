package ru.virarnd.recipeskt.ui.edit

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.github.ajalt.timberkt.Timber
import com.google.android.material.textfield.TextInputLayout
import com.travijuu.numberpicker.library.Interface.ValueChangedListener

import kotlinx.android.synthetic.main.activity_edit.*
import ru.virarnd.recipeskt.data.PendingRecipe
import ru.virarnd.recipeskt.data.Recipe
import ru.virarnd.recipeskt.data.RecipeDataProvider


class EditActivity : AppCompatActivity() {

    lateinit var pendingRecipe: PendingRecipe

    companion object {
        private val INTENT_POSITION_LABEL = "recipe_position"
        private val RECIPE_MARKER = "this_recipe"

        fun newIntent(context: Context, position: Int): Intent {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(INTENT_POSITION_LABEL, position)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ru.virarnd.recipeskt.R.layout.activity_edit)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Edit recipe"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            Timber.d { "Первый запуск!" }
            // Если первый запуск
            val recipePosition = intent.getIntExtra(INTENT_POSITION_LABEL, -1)
            // Беру исходный рецепт
            val sourceRecipe = RecipeDataProvider.getRecipe(recipePosition)
            // Помещаю во временный
            pendingRecipe = copySourceRecipeToPendingRecipe(sourceRecipe)
        } else {
            Timber.d { "Повторный запуск!" }
            url_container.removeAllViews()
            pendingRecipe = savedInstanceState.getParcelable<PendingRecipe>(RECIPE_MARKER) ?: throw IllegalArgumentException("Missed recipe!!!")
        }

        // Заполняю экран данными из временного рецепта
        initView(pendingRecipe)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(RECIPE_MARKER, pendingRecipe)
    }


    private fun initView(pendingRecipe: PendingRecipe) {
        val imagesCounter = pendingRecipe.images.size
        number_picker.value = imagesCounter
        number_picker.setValueChangedListener{}
        url_container.removeAllViews()
        for (i in 0 until imagesCounter) {
            val newEditText = EditText(this)
            newEditText.setId(View.generateViewId())
            newEditText.setText(pendingRecipe.images[i])
            newEditText.setHint("URL to picture ${i + 1}")
            newEditText.afterUrlChanged { checkThisUrl(newEditText, i) }
            val editTextParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            editTextParams.setMargins(0, 0, 0, dpToPx(8))
            val textInputLayout = TextInputLayout(this)
            textInputLayout.setId(View.generateViewId())
            val textInputLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textInputLayout.setLayoutParams(textInputLayoutParams)
            textInputLayout.addView(newEditText, editTextParams);
            url_container.addView(textInputLayout)
        }

        //TODO Создать соответствующее количество простых EditText, добавить к каждому UrlInputListener с валидацией,
        // на кнопки "+" и "-" повесить слушателя, который добавляет или убирает EditText (даже если они уже есть).
        // Создать PendingRecipe, в который будет складываться вся информация о будущем новом рецепте. Который будет заменять старый при сохранении


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


    }



    private fun checkThisUrl(editText: EditText, i: Int) {
        if (editText.text.length > 5) {
            val urlValidationResult = URLUtil.isValidUrl(editText.text.toString())
            when (urlValidationResult) {
                true -> {
                    editText.error = null
                    pendingRecipe.images[i] = editText.text.toString()
                }
                else -> {
                    editText.error = "URL ${i + 1} is invalid"
                }
            }
        }
    }

    private fun validateAllFields(): Boolean {
        return false
    }

    private fun dpToPx(dp: Int) = (dp * Resources.getSystem().getDisplayMetrics().density).toInt()

    private fun copySourceRecipeToPendingRecipe(sourceRecipe: Recipe) = PendingRecipe(
        name = sourceRecipe.name,
        persons = sourceRecipe.persons,
        images = sourceRecipe.images.toMutableList(),
        preparationTimeInMin = sourceRecipe.preparationTimeInMin,
        category = sourceRecipe.category,
        description = sourceRecipe.description,
        ingredients = sourceRecipe.ingredients.toMutableList(),
        nutritionFacts = sourceRecipe.nutritionFacts
    )


/*
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        url_container.removeAllViews()
        val recipe = savedInstanceState?.getParcelable<PendingRecipe>(RECIPE_MARKER)
        if (recipe != null) {
            pendingRecipe = recipe
        }
    }
*/
}


fun EditText.afterUrlChanged(textChanged: () -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            textChanged()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    })

}
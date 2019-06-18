package ru.virarnd.recipeskt.ui.edit

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.ajalt.timberkt.Timber
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ibotta.android.support.pickerdialogs.SupportedTimePickerDialog
import com.ibotta.android.support.pickerdialogs.SupportedTimePickerDialog.OnTimeSetListener

import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.toolbar
import ru.virarnd.recipeskt.R
import ru.virarnd.recipeskt.common.InstantAutoComplete
import ru.virarnd.recipeskt.common.toPx
import ru.virarnd.recipeskt.data.*
import ru.virarnd.recipeskt.data.NutritionFact.Type.*
import ru.virarnd.recipeskt.data.RecipeDataProvider.getRecipe


class EditActivity : AppCompatActivity() {

    lateinit var pendingRecipe: PendingRecipe
    private var recipePosition: Int = -1
    var waitingTimePicker = true
    var badUrlCounter = 0

    companion object {
        private val INTENT_POSITION_LABEL = "recipe_position"
        private val RECIPE_MARKER = "this_recipe"

        fun newIntent(context: Context, position: Int?): Intent {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(INTENT_POSITION_LABEL, position)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Edit recipe"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        savedInstanceState?.let {
            Timber.d { "Повторный запуск!" }
            pendingRecipe =
                savedInstanceState.getParcelable(RECIPE_MARKER) ?: throw IllegalArgumentException("Missed recipe!!!")
        } ?: run {
            Timber.d { "Первый запуск!" }
            // Если первый запуск
            recipePosition = intent.getIntExtra(INTENT_POSITION_LABEL, -1)
            Timber.d { "Позиция: $recipePosition" }
            //Если новый рецепт, то позиция <0 и новый рецепт создаётся со значениями "по умолчанию"
            //И в конце при сохранении проверяется эта позиция, и либо сохраняется в новый рецпет (если валидация удачная), либо удаляется
            if (recipePosition >= 0) {
                // Беру исходный рецепт
                val sourceRecipe = getRecipe(recipePosition)
                // Помещаю во временный
                pendingRecipe = createPendingRecipeFromRecipe(sourceRecipe)
            } else {
                pendingRecipe = PendingRecipe()
            }

        }


        // Заполняю экран данными из временного рецепта
        initView(pendingRecipe)
    }

    private fun createPendingRecipeFromRecipe(sourceRecipe: Recipe) = PendingRecipe(
        name = sourceRecipe.name,
        persons = sourceRecipe.persons,
        images = sourceRecipe.images.toMutableList(),
        preparationTimeInMin = sourceRecipe.preparationTimeInMin,
        category = sourceRecipe.category,
        description = sourceRecipe.description,
        ingredients = sourceRecipe.ingredients.toMutableList(),
        nutritionFacts = sourceRecipe.nutritionFacts
    )


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(RECIPE_MARKER, pendingRecipe)
    }


    private fun initView(pendingRecipe: PendingRecipe) {
        // Часть, ответственная за ввод URL для картинок
        val imagesCounter = pendingRecipe.images.size
        number_picker.value = imagesCounter
        number_picker.setValueChangedListener { value, _ -> changeUrlsRows(newValue = value) }
        url_container.removeAllViews()
        for (i in 0 until imagesCounter) {
            val newEditText = TextInputEditText(this)
            newEditText.id = View.generateViewId()
            newEditText.setText(pendingRecipe.images[i])
            newEditText.hint = "URL to picture ${i + 1}"
            newEditText.afterUrlChanged { checkThisUrl(newEditText, i) }
            val editTextParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            editTextParams.setMargins(0, 0, 0, 8.toPx)
            val textInputLayout = TextInputLayout(this)
            textInputLayout.id = View.generateViewId()
            val textInputLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textInputLayout.layoutParams = textInputLayoutParams
            textInputLayout.addView(newEditText, editTextParams)
            url_container.addView(textInputLayout)
        }

        // Ввод имени
        ti_et_name_edit.setText(pendingRecipe.name)
        // Ввод количества человек и времени приготовления
        val spinnerPersonsAdapter =
            ArrayAdapter(this, R.layout.item_simple_spinner, R.id.tv_item_persons, listOf(1, 2, 3, 4, 5, 6, 7, 8))
        with(autocomplete_tv_persons_edit) {
            setText(pendingRecipe.persons.toString())
            setAdapter(spinnerPersonsAdapter)
            setOnTouchListener { view, _ -> showAutocompleteDropDown(view) }
            setOnItemClickListener { _, _, position, _ -> setNewPersonsValue(position) }
        }
        ti_persons_edit.setOnTouchListener { _, _ -> showAutocompleteDropDown(autocomplete_tv_persons_edit) }

        // Ввод времени приготовления, TimePicker
        showTime(pendingRecipe)

        // Ввод категории
        val strArray = Category.values().map { e -> baseContext.getString(e.resourceId) }
        val spinnerCategoryAdapter =
            ArrayAdapter(this, R.layout.item_simple_spinner, R.id.tv_item_persons, strArray)
        with(autocomplete_tv_category_edit) {
            setText(baseContext.getString(pendingRecipe.category.resourceId))
            setAdapter(spinnerCategoryAdapter)
            setOnTouchListener { view, _ -> showAutocompleteDropDown(view) }
            setOnItemClickListener { _, _, position, _ -> setNewCategory(strArray[position]) }
        }
        ti_category_edit.setOnTouchListener { _, _ -> showAutocompleteDropDown(autocomplete_tv_category_edit) }

        // Ввод описания рецепта
        ti_et_description_edit.imeOptions = EditorInfo.IME_ACTION_DONE
        ti_et_description_edit.setRawInputType(InputType.TYPE_CLASS_TEXT)
        ti_et_description_edit.setText(pendingRecipe.description)

        // Ввод ингредиентов
        ti_et_ingredients_edit.setText(pendingRecipe.ingredients.joinToString(separator = "\n"))

        val calories = pendingRecipe.nutritionFacts.find { e -> e.type == NutritionFact.Type.CALORIES }
        calories?.let {
            val categoryVal = calories.value
            ti_et_calories_value.setText(categoryVal.toString())
        }

        val protein = pendingRecipe.nutritionFacts.find { e -> e.type == NutritionFact.Type.PROTEIN }
        protein?.let {
            val proteinVal = protein.value
            ti_et_protein_value.setText(proteinVal.toString())
        }

        val fat = pendingRecipe.nutritionFacts.find { e -> e.type == NutritionFact.Type.FAT }
        fat?.let {
            val fatVal = fat.value
            ti_et_fat_value.setText(fatVal.toString())
        }

        val carbs = pendingRecipe.nutritionFacts.find { e -> e.type == NutritionFact.Type.CARBS }
        carbs?.let {
            val carbsVal = carbs.value
            ti_et_carbs_value.setText(carbsVal.toString())
        }

        fab_edit.setOnClickListener { trySaveAndFinish() }
    }

    private fun showTime(pendingRecipe: PendingRecipe) {
        val timeStr = "${pendingRecipe.preparationTimeInMin} min"
        ti_et_preparation_time_edit.setText(timeStr)
        ti_et_preparation_time_edit.setOnTouchListener { _, _ -> callTimePickerDialog() }
    }

    private fun callTimePickerDialog(): Boolean {
        if (waitingTimePicker) {
            waitingTimePicker = false
            Timber.d { "Call TimePickerDialog" }

            val onTimeSetListener = object : OnTimeSetListener {
                override fun onTimeSet(view: TimePicker, hours: Int, minutes: Int) {
                    setNewCookingTime(hours, minutes)
                    waitingTimePicker = true
                }
            }
            val recipeHours = pendingRecipe.preparationTimeInMin / 60
            val recipeMinutes = pendingRecipe.preparationTimeInMin % 60
            val timePicker = SupportedTimePickerDialog(
                this,
                R.style.SpinnerTimePickerDialogTheme,
                onTimeSetListener,
                recipeHours,
                recipeMinutes,
                true
            )
            timePicker.setTitle("Select preparation time")
            timePicker.setOnCancelListener { waitingTimePicker = true }
            timePicker.setCancelable(false)
            timePicker.setCanceledOnTouchOutside(false)
            timePicker.show()
            return true
        } else
            return false
    }

    private fun setNewCookingTime(hours: Int, minutes: Int) {
        val hoursStr = if (hours > 0) "Hours: ${hours}, m" else "M"
        Timber.d { "${hoursStr}inutes: ${minutes}" }
        pendingRecipe.preparationTimeInMin = hours * 60 + minutes
        showTime(pendingRecipe)
//        initView(pendingRecipe)
    }

    private fun setNewPersonsValue(position: Int) {
        pendingRecipe.persons = position + 1
    }

    private fun setNewCategory(categoryName: String) {
        val newCategory: Category =
            Category.values().find { e -> baseContext.getString(e.resourceId) == categoryName }!!
        Timber.d { "Find category! ${newCategory.name}" }
        pendingRecipe.category = newCategory
    }

    private fun showAutocompleteDropDown(view: View): Boolean {
        view.isFocusableInTouchMode = false
        (view as InstantAutoComplete).showDropDown()
        return false
    }


    private fun changeUrlsRows(newValue: Int) {
        Timber.d { "New value for rows = ${newValue}" }
        with(pendingRecipe) {
            if (newValue > images.size) {
                images.add("")
            } else if (newValue < images.size) {
                images.remove(images.last())
            }
            initView(pendingRecipe)
        }
    }


    private fun checkThisUrl(editText: EditText, i: Int) {
        if (editText.text.length > 5) {
            val urlValidationResult = URLUtil.isValidUrl(editText.text.toString())
            when (urlValidationResult) {
                true -> {
                    editText.error = null
                    pendingRecipe.images[i] = editText.text.toString()
                    if (badUrlCounter > 0) badUrlCounter--
                }
                else -> {
                    editText.error = "URL ${i + 1} is invalid"
                    badUrlCounter++
                }
            }
        }
    }

    private fun validateAllFields(): Boolean {
        if (badUrlCounter > 0) {
            Toast.makeText(this, "Error! Check all fields!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (ti_et_name_edit.text?.length!! < 3) {
            ti_name_edit.error = "Text too short"
            ti_et_name_edit.error = ""
            ti_et_name_edit.clearFocus()
            return false
        } else
            ti_name_edit.error = null

        if (autocomplete_tv_persons_edit.text.toString().isEmpty()) {
            autocomplete_tv_persons_edit.error = ""
            ti_persons_edit.error = "Error: empty field"
            return false
        } else {
            autocomplete_tv_persons_edit.error = null
            ti_persons_edit.error = null
        }

        if (autocomplete_tv_category_edit.text.toString().isEmpty()) {
            autocomplete_tv_category_edit.error = ""
            ti_category_edit.error = "Error: empty field"
            return false
        } else {
            autocomplete_tv_category_edit.error = null
            ti_category_edit.error = null
        }

        if (ti_et_description_edit.text.toString().isEmpty()) {
            ti_description_edit.error = "Error: empty field"
            ti_et_description_edit.error = ""
            ti_et_name_edit.clearFocus()
            return false
        } else if (ti_et_description_edit.length() > ti_description_edit.counterMaxLength) {
            ti_description_edit.error = "Too many characters!"
            return false
        } else {
            ti_description_edit.error = null
            ti_et_description_edit.error = null
        }

        if (ti_et_ingredients_edit.text.toString().isEmpty()) {
            ti_ingredients_edit.error = "Error: empty field"
            ti_et_ingredients_edit.error = ""
            return false
        } else if (ti_et_ingredients_edit.length() > ti_ingredients_edit.counterMaxLength) {
            ti_ingredients_edit.error = "Too many characters!"
            return false
        } else {
            ti_ingredients_edit.error = null
            ti_et_ingredients_edit.error = null
        }
        return true
    }

    private fun trySaveAndFinish(): Boolean {
        if (validateAllFields()) {
//            Toast.makeText(this, "All correct!", Toast.LENGTH_LONG).show()
            saveFieldsToPending()
            //При редактировании существующего он сохраняется,
            //если новый -- записывается в новый рецепт в общем списке.
            if (recipePosition >= 0) {
                RecipeDataProvider.updateRecipe(recipePosition, pendingRecipe)
            } else {
                RecipeDataProvider.addNewEmptyRecipe()
                RecipeDataProvider.updateRecipe(RecipeDataProvider.getSize() - 1, pendingRecipe)
            }
            finish()
            return true
        } else
            return false
    }

    override fun onResume() {
        Timber.d{"EditActivity onResume"}
        super.onResume()
    }

    override fun onPause() {
        Timber.d{"EditActivity onPause"}
        super.onPause()
    }

    private fun saveFieldsToPending() {
        with(pendingRecipe) {
            name = ti_et_name_edit.text.toString()
            description = ti_et_description_edit.text.toString()
            ingredients = ti_et_ingredients_edit.text.toString().split("\n").toMutableList()
            pendingRecipe.nutritionFacts.clear()

            val caloriesVal = ti_et_calories_value.text.toString().toDoubleOrNull() ?: -1.0
            val proteinVal = ti_et_protein_value?.text.toString().toDoubleOrNull() ?: -1.0
            val fatVal = ti_et_fat_value?.text.toString().toDoubleOrNull() ?: -1.0
            val carbsVal = ti_et_carbs_value?.text.toString().toDoubleOrNull() ?: -1.0

            if (caloriesVal >= 0) {
                pendingRecipe.nutritionFacts.add(NutritionFact(CALORIES, caloriesVal))
            }
            if (proteinVal >= 0) {
                pendingRecipe.nutritionFacts.add(NutritionFact(PROTEIN, proteinVal))
            }
            if (fatVal >= 0) {
                pendingRecipe.nutritionFacts.add(NutritionFact(FAT, fatVal))
            }
            if (carbsVal >= 0) {
                pendingRecipe.nutritionFacts.add(NutritionFact(CARBS, carbsVal))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (trySaveAndFinish()) {
            return
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Click BACK again to exit without saving", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
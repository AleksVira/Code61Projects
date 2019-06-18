package ru.virarnd.recipeskt.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PendingRecipe(
    var name: String = "",
    var persons: Int = 1,
    var images: MutableList<String> = mutableListOf(""),
    var preparationTimeInMin: Int = 0,
    var category: Category = Category.values()[0],
    var description: String = "",
    var ingredients: MutableList<String> = mutableListOf(""),
    var nutritionFacts: HashSet<NutritionFact> = hashSetOf()
) : Parcelable
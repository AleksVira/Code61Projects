package ru.virarnd.recipeskt.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PendingRecipe(
    var name: String,
    var persons: Int,
    val images: MutableList<String>,
    var preparationTimeInMin: Int,
    var category: Category,
    var description: String,
    val ingredients: MutableList<String>,
    var nutritionFacts: HashSet<NutritionFact>
) : Parcelable
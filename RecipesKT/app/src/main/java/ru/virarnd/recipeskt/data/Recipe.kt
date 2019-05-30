package ru.virarnd.recipeskt.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val name: String,
    val persons: Int,
    val images: List<String>,
    val preparationTimeInMin: Int,
    val category: Category,
    val description: String,
    val ingredients: List<String>,
    val nutritionFacts: HashSet<NutritionFact>
) : Parcelable


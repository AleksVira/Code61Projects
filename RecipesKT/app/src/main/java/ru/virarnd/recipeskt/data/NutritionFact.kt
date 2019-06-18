package ru.virarnd.recipeskt.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NutritionFact(
    val type: Type,
    val value: Double
) : Parcelable {
    enum class Type {
        CALORIES, PROTEIN, FAT, CARBS;
    }
}
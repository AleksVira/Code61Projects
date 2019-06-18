package ru.virarnd.recipeskt.data

fun PendingRecipe.createRecipeFromPendingRecipe(sourceRecipe: PendingRecipe) = Recipe(
    name = sourceRecipe.name,
    persons = sourceRecipe.persons,
    images = sourceRecipe.images.toMutableList(),
    preparationTimeInMin = sourceRecipe.preparationTimeInMin,
    category = sourceRecipe.category,
    description = sourceRecipe.description,
    ingredients = sourceRecipe.ingredients.toMutableList(),
    nutritionFacts = sourceRecipe.nutritionFacts
)

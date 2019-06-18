package ru.virarnd.recipeskt.data

import ru.virarnd.recipeskt.data.NutritionFact.*

object RecipeDataProvider : DataProvider<List<Recipe>> {

    private var recipes: MutableList<Recipe>

    init {
        recipes = provide()
    }

    override fun isPresent(recipe: Recipe): Boolean {
        //TODO Реализовать поиск по имени среди рецептов.
        // Не может быть рецептов с одинаковыми названиями
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addNewEmptyRecipe() {
        recipes.add(PendingRecipe().createRecipeFromPendingRecipe(PendingRecipe()))

    }

    override fun updateRecipe(position: Int, newRecipe: PendingRecipe) {
        val newRecipeToList = with(newRecipe) {
            Recipe(
                name = name,
                persons = persons,
                images = images,
                preparationTimeInMin = preparationTimeInMin,
                category = category,
                description = description,
                ingredients = ingredients,
                nutritionFacts = nutritionFacts
            )
        }
        recipes.set(position, newRecipeToList)
    }

    override fun provide() = mutableListOf(
        Recipe(
            name = "Fantastic Black Bean Chili",
            persons = 4,
            images = listOf(
                "https://s3.amazonaws.com/finecooking.s3.tauntonclud.com/app/uploads/2017/04/18165342/051133001-slow-cooker-short-ribs-recipe-main.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyyZmirlfL5RWpe5NkDvC6tTVSj-MTtAbXxaJvX-Kr3Y40IlbI"
            ),
            preparationTimeInMin = 55,
            category = Category.MAIN_DISH,
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nibh mauris cursus mattis molestie a iaculis at erat pellentesque. Posuere ac ut consequat semper viverra. Nunc sed id semper risus in hendrerit gravida rutrum. Quam vulputate dignissim suspendisse in est. Maecenas sed enim ut sem viverra aliquet eget sit amet. At in tellus integer feugiat scelerisque varius morbi enim. At augue eget arcu dictum varius duis at. Sit amet luctus venenatis lectus magna. Purus semper eget duis at tellus at. Sit amet est placerat in. Accumsan sit amet nulla facilisi. Vivamus arcu felis bibendum ut tristique et. Posuere ac ut consequat semper viverra. Pharetra sit amet aliquam id diam maecenas ultricies mi eget. Adipiscing at in tellus integer feugiat scelerisque varius morbi enim.",
            ingredients = listOf(
                "1/2 onion, minced",
                "2 tablespoon olive oil",
                "3 cloves garlic",
                "1/2 teaspoon freshly ground black pepper",
                "1/3 cup dry bread crumbs",
                "1/4 cup packed chopped Italian parsley",
                "1 pound ground beef"
            ),
            nutritionFacts = hashSetOf(
                NutritionFact(Type.CALORIES, 331.0),
                NutritionFact(Type.PROTEIN, 16.3),
                NutritionFact(Type.FAT, 20.9),
                NutritionFact(Type.CARBS, 18.9)
            )
        ),
        Recipe(
            name = "Chicken Caesar Salad",
            persons = 1,
            images = listOf(
                "https://media.gettyimages.com/photos/chicken-meal-picture-id154954255?s=1024x1024",
                "https://media.gettyimages.com/photos/caesar-salad-picture-id585088567?s=1024x1024",
                "https://media.gettyimages.com/photos/bowl-of-caesar-salad-with-croutons-and-cheese-on-table-picture-id184938052?s=1024x1024"
            ),
            preparationTimeInMin = 15,
            category = Category.SALAD,
            description = "Slice the raw chicken breast into strips and fry in a pan with olive oil. Mix the roasted chicken, lettuce, dressing, and cheese in a bowl. Eat it.",
            ingredients = listOf(
                "1/2 cup high",
                "4 cloves fresh garlic",
                "1 baguette",
                "1/4 cup lemon juice",
                "4 ounces Parmesan cheese",
                "2 eggs"
            ),
            nutritionFacts = hashSetOf(
                NutritionFact(Type.CALORIES, 295.0),
                NutritionFact(Type.PROTEIN, 32.0),
                NutritionFact(Type.FAT, 16.0),
                NutritionFact(Type.CARBS, 6.0)
            )
        )
    )

    fun getRecipe(position: Int): Recipe {
        return recipes[position]
    }

    fun getRecipesList(): List<Recipe> {
        return recipes
    }

    fun getSize() = recipes.size
}
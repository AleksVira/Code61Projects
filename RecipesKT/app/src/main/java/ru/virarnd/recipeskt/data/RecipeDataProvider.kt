package ru.virarnd.recipeskt.data

import ru.virarnd.recipeskt.data.NutritionFact.*

object RecipeDataProvider : DataProvider<List<Recipe>> {
    override fun isPresent(recipe: Recipe): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addRecipe(recipe: Recipe) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateRecipe(recipe: Recipe) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun provide() = listOf(
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
                CALORIES.apply { value = 331.0 },
                PROTEIN.apply { value = 16.3 },
                FAT.apply { value = 20.9 },
                CARBS.apply { value = 18.9 }
            )
        )
    )

}
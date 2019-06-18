package ru.virarnd.recipeskt.data

import ru.virarnd.recipeskt.R

enum class Category(var resourceId: Int) {
    BREAKFAST(R.string.breakfast),
    LUNCH(R.string.lunch),
    SOUP(R.string.soup),
    SALAD(R.string.salad),
    MAIN_DISH(R.string.main_dish);

}
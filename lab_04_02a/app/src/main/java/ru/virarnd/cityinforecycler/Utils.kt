package ru.virarnd.cityinforecycler

const val RESULT_EMPTY_FIELDS = 0
const val RESULT_GOOD_NEW = 1
const val RESULT_BAD_REPEAT = 2

fun emptyFields(cityName: String, country: String) =
    cityName.isEmpty() || country.isEmpty()

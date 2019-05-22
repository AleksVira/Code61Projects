package ru.virarnd.cityinforecycler

import java.util.*

data class CityInfo(val cityName: String, val country: String, val id: String = UUID.randomUUID().toString())

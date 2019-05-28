package ru.virarnd.labrecyclerview.data

class RepoCityInfo {
    fun getData(): List<CityInfo> {
        val cityes = listOf(CityInfo("AA", "Ru"), CityInfo("BB", "Ru"), CityInfo("CC", "Ru"), CityInfo("DD", "Fr"))
        return cityes
    }
}
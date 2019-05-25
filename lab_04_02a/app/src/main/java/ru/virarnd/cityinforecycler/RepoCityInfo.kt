package ru.virarnd.cityinforecycler

object RepoCityInfo {
    private var cities: MutableList<CityInfo> = arrayListOf()

    init {
        cities.add(CityInfo("AA", "Ru"))
        cities.add(CityInfo("BB", "En"))
        cities.add(CityInfo("CC", "Fr"))
        cities.add(CityInfo("DD", "Us"))
        cities.add(CityInfo("EE", "Ru"))
        cities.add(CityInfo("FF", "Ru"))
        cities.add(CityInfo("GG", "Ru"))
        cities.add(CityInfo("HH", "Ru"))
    }

    fun getCities() = cities

    fun checkCity(cityName: String, country: String): Int {
        when {
            emptyFields(cityName, country) -> return RESULT_EMPTY_FIELDS
            isNewCity(cityName, country) -> return RESULT_GOOD_NEW;
            else -> return RESULT_BAD_REPEAT
        }
    }

    fun isNewCity(testCityName: String, testCountry: String) =
        !cities.any { cityInfo -> cityInfo.cityName == testCityName && cityInfo.country == testCountry }

    fun setCities(newCities: MutableList<CityInfo>) {
        cities = newCities
    }
}
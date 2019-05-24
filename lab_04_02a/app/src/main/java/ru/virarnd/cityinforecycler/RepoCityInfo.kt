package ru.virarnd.cityinforecycler

object RepoCityInfo {
    var cities: ArrayList<CityInfo> = arrayListOf()

    init{
        cities.add(CityInfo("AA", "Ru"))
        cities.add(CityInfo("BB", "En"))
        cities.add(CityInfo("CC", "Fr"))
        cities.add(CityInfo("DD", "Us"))
        cities.add(CityInfo("EE", "Ru"))
        cities.add(CityInfo("FF", "Ru"))
        cities.add(CityInfo("GG", "Ru"))
        cities.add(CityInfo("HH", "Ru"))
    }

    fun addNewCity(cityName: String, country: String): Int {
        if (isNewCity(cityName, country)) {
            cities.add(CityInfo(cityName, country))
            return RESULT_GOOD;
        } else
            return RESULT_BAD
    }

    fun isNewCity(testCityName: String, testCountry: String) =
        !cities.any{cityInfo -> cityInfo.cityName == testCityName && cityInfo.country == testCountry }
}
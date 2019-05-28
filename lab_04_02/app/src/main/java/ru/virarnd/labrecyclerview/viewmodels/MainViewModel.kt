package ru.virarnd.labrecyclerview.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.virarnd.labrecyclerview.data.CityInfo

class MainViewModel : ViewModel() {

    private val _cities: MutableLiveData<List<CityInfo>> = MutableLiveData()
    val cities: LiveData<List<CityInfo>> = _cities


    init{
        val newCity = CityInfo("A", "Ru")
        _cities(listOf(newCity))
    }

}
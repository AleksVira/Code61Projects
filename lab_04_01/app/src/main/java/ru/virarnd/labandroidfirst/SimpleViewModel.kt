package ru.virarnd.labandroidfirst

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SimpleViewModel(val myApplication: Application) : AndroidViewModel(myApplication) {
    private val TAG = SimpleViewModel::class.java.name

    private val _counter: MutableLiveData<Int> = MutableLiveData(0)
    val counter: LiveData<Int> = _counter

//    private val _toastString: MutableLiveData<String?> = MutableLiveData(null)
//    val toastString: LiveData<String?> = _toastString


    val APP_PREFERENCES = "allSettings"
    val APP_PREFERENCES_COUNTER = "counter"
    val pref: SharedPreferences = myApplication.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

    init{
        if (pref.contains(APP_PREFERENCES_COUNTER)) {
            val counterFromPrefs = pref.getInt(APP_PREFERENCES_COUNTER, 0);
            _counter.value = counterFromPrefs
        }
    }

    fun onMinusClicked() {
        Log.d(TAG, "Minus clicked!")
        when (_counter.value) {
            0, 1 -> {
                _counter.value = 0
//                _toastString.value = "Достигнуто минимальное значение!"
                Toast.makeText(myApplication, "Достигнуто минимальное значение!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                _counter.value = (_counter.value ?: 0) - 1
//                _toastString.value = null
            }
        }
    }

    fun onPlusClicked() {
        Log.d(TAG, "Plus clicked!")
        when (_counter.value) {
            9, 10 -> {
                _counter.value = 10
                Toast.makeText(myApplication, "Достигнуто максимальное значение!", Toast.LENGTH_SHORT).show()
//                _toastString.value = "Достигнуто максимальное значение!"
            }
            else -> {
                _counter.value = (_counter.value ?: 0) + 1
//                _toastString.value = null
            }
        }
    }

    // При убиении Активити сохраняю в SharedPreferences
    override fun onCleared() {
        super.onCleared()
        val counterNow = _counter.value
        val editor = pref.edit()
        counterNow?.let { editor.putInt(APP_PREFERENCES_COUNTER, it) }
        editor.apply()
    }
}
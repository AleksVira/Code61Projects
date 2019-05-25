package ru.virarnd.cityinforecycler

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: CityInfoAdapter

    companion object {
        const val EDIT_CITY_INFO_REQUEST = 1
        const val POSITION_KEY = "Position Key"
        const val CITY_INFO_KEY = "City Key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cities = RepoCityInfo.getCities()
        adapter = CityInfoAdapter { cityInfo: CityInfo, position: Int -> cityInfoItemClicked(cityInfo, position) }
        city_recyclerView.adapter = adapter
        adapter.updateCities(cities)

        bt_add.setOnClickListener {
            val newCityName = et_city.text.toString()
            val newCountry = et_country.text.toString()
            val addResult: Int = RepoCityInfo.checkCity(newCityName, newCountry)
            when (addResult) {
                RESULT_EMPTY_FIELDS -> {
                    Toast.makeText(this, "Ошибка ввода! Добавьте данных", Toast.LENGTH_SHORT).show()
                }
                RESULT_BAD_REPEAT -> {
                    Toast.makeText(this, "Ошибка! Такой город уже есть", Toast.LENGTH_SHORT).show()
                }
                RESULT_GOOD_NEW -> {
                    adapter.addNewCity(newCityName, newCountry)
                    et_city.text.clear()
                    et_country.text.clear()
                }
            }
        }
    }

    fun cityInfoItemClicked(cityInfo: CityInfo, position: Int) {
        Toast.makeText(this, "Clicked: ${cityInfo.cityName}, position = $position", Toast.LENGTH_LONG).show()
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra(POSITION_KEY, position)
        intent.putExtra(CITY_INFO_KEY, cityInfo)
        startActivityForResult(intent, EDIT_CITY_INFO_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != EDIT_CITY_INFO_REQUEST || data == null) return
        if (resultCode == Activity.RESULT_OK) {
            // Поскольку изменяется только один элемент, с известной позицией, можно обойтись  notifyItemChanged()
            val position = data.getIntExtra(POSITION_KEY, 0)
            adapter.updateOneCity(position)
        }
    }
}
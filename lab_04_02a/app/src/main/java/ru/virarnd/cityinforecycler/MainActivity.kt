package ru.virarnd.cityinforecycler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import ru.virarnd.cityinforecycler.RepoCityInfo.cities

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

        val cities = cities
        adapter =
            CityInfoAdapter(cities, { cityInfo: CityInfo, position: Int -> cityInfoItemClicked(cityInfo, position) })
        city_recyclerView.adapter = adapter

        bt_add.setOnClickListener {
            val newCityName = et_city.text.toString()
            val newCountry = et_country.text.toString()
            val addResult: Int = RepoCityInfo.addNewCity(newCityName, newCountry)
            when {
                newCityName.isEmpty() || newCountry.isEmpty() -> {
                    Toast.makeText(this, "Ошибка ввода! Добавьте данных", Toast.LENGTH_SHORT).show()
                }
                addResult == RESULT_BAD -> {
                    Toast.makeText(this, "Ошибка! Такой город уже есть", Toast.LENGTH_SHORT).show()
                }
                addResult == RESULT_GOOD -> {
                    et_city.text.clear()
                    et_country.text.clear()
                    adapter.notifyDataSetChanged()
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
        adapter.notifyDataSetChanged()
    }
}
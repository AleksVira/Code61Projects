package ru.virarnd.cityinforecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val repoCityInfo = RepoCityInfo()
    lateinit var etCityName: EditText
    lateinit var etCountry: EditText
    lateinit var btAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCityName = findViewById(R.id.et_city)
        etCountry = findViewById(R.id.et_country)
        btAdd = findViewById(R.id.bt_add)

        val recycler = findViewById(R.id.city_recyclerView) as RecyclerView
        val cities = repoCityInfo.cities
        val adapter = CityInfoAdapter(cities)
        recycler.adapter = adapter

        btAdd.setOnClickListener {
            val newCityName= etCityName.text.toString()
            val newCountry= etCountry.text.toString()
            val addResult: Int = repoCityInfo.addNewCity(newCityName, newCountry)
            when (addResult) {
                RESULT_BAD -> {
                    Toast.makeText(this, "Ошибка! Такой город уже есть", Toast.LENGTH_SHORT).show()}
                RESULT_GOOD -> {
                    etCityName.text.clear()
                    etCountry.text.clear()
                }
            }
        }
    }
}
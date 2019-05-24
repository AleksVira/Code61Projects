package ru.virarnd.cityinforecycler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    companion object {
        const val POSITION_RESULT_KEY = "Position Result Key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val position = intent.getIntExtra(MainActivity.POSITION_KEY, 0)
        val cityInfo: CityInfo? = intent.getParcelableExtra(MainActivity.CITY_INFO_KEY)

        val startCountry = cityInfo?.country
        val startCity = cityInfo?.cityName

        ti_et_country_edit.setText(startCountry)
        ti_et_city_edit.setText(startCity)

        bt_save.setOnClickListener {
            val editCityName = ti_et_city_edit.text.toString()
            val editCountry = ti_et_country_edit.text.toString()
            val editResult: Int = RepoCityInfo.checkCity(editCityName, editCountry)

            when (editResult) {
                RESULT_EMPTY_FIELDS -> {
                    Toast.makeText(this, "Ошибка ввода! Добавьте данных", Toast.LENGTH_SHORT).show()
                }
                RESULT_BAD_REPEAT -> {
                    if (editCityName == startCity && editCountry == startCountry) finish() else
                    Toast.makeText(this, "Ошибка! Такой город уже есть", Toast.LENGTH_SHORT).show()
                }
                RESULT_GOOD_NEW -> {
                    RepoCityInfo.cities[position].cityName = editCityName
                    RepoCityInfo.cities[position].country = editCountry
                    val goodIntent = Intent().putExtra(POSITION_RESULT_KEY, position)
                    setResult(Activity.RESULT_OK, goodIntent)
                    finish()
                }
            }
        }
    }
}


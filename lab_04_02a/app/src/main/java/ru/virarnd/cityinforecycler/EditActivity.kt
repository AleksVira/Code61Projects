package ru.virarnd.cityinforecycler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import ru.virarnd.cityinforecycler.MainActivity.Companion.CITY_INFO_KEY
import ru.virarnd.cityinforecycler.MainActivity.Companion.POSITION_KEY

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val position = intent.getIntExtra(POSITION_KEY, 0)
        val cityInfo: CityInfo? = intent.getParcelableExtra(CITY_INFO_KEY)

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
                    val goodIntent = Intent()
                    goodIntent.putExtra(POSITION_KEY, position)
                    goodIntent.putExtra(CITY_INFO_KEY, CityInfo(editCityName, editCountry))
                    RepoCityInfo.getCities()[position] = CityInfo(editCityName, editCountry)
                    setResult(Activity.RESULT_OK, goodIntent)
                    finish()
                }
            }
        }
    }
}


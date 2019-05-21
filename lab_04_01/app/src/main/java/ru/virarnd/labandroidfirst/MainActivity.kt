package ru.virarnd.labandroidfirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.virarnd.labandroidfirst.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SimpleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        // Сначала сделал через подписку на состояние строки toastString, и выкидывал Toast здесь.
        // Но так как ViewModel пришлось наследовать AndroidViewModel (для доступа к SharedPreferences),
        // то там уже есть Context, который позволяет показать Toast из SimpleViewModel
        // Или это плохой путь и надо было доступ к SharedPreferences по другому реализовать?

/*
        viewModel.toastString.observe(this, Observer { toastString ->
            toastString?.let {
                Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show()
            }
        })
*/
    }
}
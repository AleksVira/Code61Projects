package ru.virarnd.recipeskt.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import ru.virarnd.recipeskt.R
import ru.virarnd.recipeskt.common.*
import ru.virarnd.recipeskt.ui.detail.DetailFragment
import ru.virarnd.recipeskt.ui.edit.EditFragment

class MainActivity : AppCompatActivity(), HomeFlow {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            openMainList()
        }
    }


    override fun openMainList() {
        val fragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, MAIN_FRAGMENT_TAG)
            .addToBackStack(MAIN_FRAGMENT_TAG)
            .commit()
    }

    override fun openDetails(position: Int) {
        val fragment = DetailFragment.newInstance(position)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, DETAIL_FRAGMENT_TAG)
            .addToBackStack(DETAIL_FRAGMENT_TAG)
            .commit()
    }

    override fun openEdit(position: Int) {
        val fragment = EditFragment.newInstance(position)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, EDIT_FRAGMENT_TAG)
            .addToBackStack(EDIT_FRAGMENT_TAG)
            .commit()
    }

    override fun changeTitle(title: String?) {
        supportActionBar?.title = title
    }

    override fun changeHomeAsUpEnabled(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun onBackPressed() {
        // Если фрагмент ловит нажатия назаж, перехватываю и обрабатываю
        if (sendBackPressToFragmentOnTop()) return
        else checkExit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Если фрагмент ловит нажатия назаж, перехватываю и обрабатываю
                if (sendBackPressToFragmentOnTop()) return true
                checkExit()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun sendBackPressToFragmentOnTop(): Boolean {
        // Проверяю, поддерживает ли текущий фрагмент обработку нажатия назад
        val fragmentOnTop = supportFragmentManager.fragments.last() ?: return false
        return if (fragmentOnTop !is BackButtonSupportFragment) {
            false
        } else {
            if (fragmentOnTop.onBackPressed()) checkExit()
            true
        }
    }

    private fun checkExit() {
        // Если в стэке один фрагмент и нажато "назад", то выхожу
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else finish()
    }


}

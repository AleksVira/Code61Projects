package ru.virarnd.recipeskt.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.facebook.drawee.view.SimpleDraweeView
import ru.virarnd.recipeskt.R
import ru.virarnd.recipeskt.data.Recipe

class DetailPagerAdapter(private val recipe: Recipe) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val layout = inflater.inflate(R.layout.item_detail, container, false)
        val simpleDraweeView = layout.findViewById<SimpleDraweeView>(R.id.iv_recipe_detail)
        simpleDraweeView.setImageURI(recipe.images[position])
        container.addView(layout)
        return layout
    }


    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun getCount() = recipe.images.size

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }
}
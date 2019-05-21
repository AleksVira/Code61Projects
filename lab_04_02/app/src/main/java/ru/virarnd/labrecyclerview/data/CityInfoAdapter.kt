package ru.virarnd.labrecyclerview.data

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.labrecyclerview.TextItemViewHolder

class CityInfoAdapter : RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<CityInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.tvCityName.text = item.cityName
        holder.tvCountry.text = item.country
    }
}
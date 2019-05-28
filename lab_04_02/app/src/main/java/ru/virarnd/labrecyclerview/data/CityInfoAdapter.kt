package ru.virarnd.labrecyclerview.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.labrecyclerview.R

class CityInfoAdapter : RecyclerView.Adapter<CityInfoAdapter.CityInfoHolder>() {

    var data = listOf<CityInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityInfoHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)
//        return CityInfoHolder(inflater, parent)
        return CityInfoHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CityInfoHolder, position: Int) {
        val cityInfo = data[position]
        holder.bind(cityInfo)
    }

    inner class CityInfoHolder(view: View) :
//    inner class CityInfoHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(view) {
        private var tvCityName: TextView? = null
        private var tvCountry: TextView? = null

        init {
            tvCityName = itemView.findViewById(R.id.tv_city_name) as TextView
            tvCountry = itemView.findViewById(R.id.tv_country) as TextView
        }

        fun bind(cityInfo: CityInfo) {
            tvCityName?.text = cityInfo.cityName
            tvCountry?.text = cityInfo.country
        }
    }
}
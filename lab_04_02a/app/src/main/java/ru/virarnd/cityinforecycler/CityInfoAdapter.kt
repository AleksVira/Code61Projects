package ru.virarnd.cityinforecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityInfoAdapter (val citiesList: ArrayList<CityInfo>): RecyclerView.Adapter<CityInfoAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = citiesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCityName.text = citiesList[position].cityName
        holder.tvCountry.text = citiesList[position].country
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvCityName = itemView.findViewById(R.id.tv_city_name) as TextView
        val tvCountry = itemView.findViewById(R.id.tv_country) as TextView
    }
}
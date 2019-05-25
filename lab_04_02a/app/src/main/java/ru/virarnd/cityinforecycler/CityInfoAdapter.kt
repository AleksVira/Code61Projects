package ru.virarnd.cityinforecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_item.view.*

class CityInfoAdapter(val clickListener: (CityInfo, Int) -> Unit) : RecyclerView.Adapter<CityInfoAdapter.ViewHolder>() {

    private var citiesList: MutableList<CityInfo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = citiesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position, clickListener)

    fun updateCities(newCities: MutableList<CityInfo>) {
        val cityInfoDiffUtilCallback = CityInfoDiffUtilCallback(citiesList, newCities)
        val cityInfoDiffResult = DiffUtil.calculateDiff(cityInfoDiffUtilCallback)
        cityInfoDiffResult.dispatchUpdatesTo(this)
        RepoCityInfo.setCities(newCities)
        citiesList = newCities
    }

    fun updateOneCity(itemPosition: Int) {
//        citiesList[itemPosition] = city
        notifyItemChanged(itemPosition)
    }

    fun addNewCity(newCityName: String, newCountry: String) {
        val newCitiesList = citiesList.toMutableList()
        newCitiesList.add(CityInfo(cityName = newCityName, country = newCountry))
        updateCities(newCitiesList)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(pos: Int, clickListener: (CityInfo, Int) -> Unit) = with(itemView) {
            val cityInfo = citiesList[pos]
            tv_city_name.text = cityInfo.cityName
            tv_country.text = cityInfo.country
            setOnClickListener { clickListener(cityInfo, pos) }
        }
    }
}
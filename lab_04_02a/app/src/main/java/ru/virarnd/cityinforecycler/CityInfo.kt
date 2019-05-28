package ru.virarnd.cityinforecycler

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class CityInfo(var cityName: String, var country: String, val id: String = UUID.randomUUID().toString()) :
    Parcelable
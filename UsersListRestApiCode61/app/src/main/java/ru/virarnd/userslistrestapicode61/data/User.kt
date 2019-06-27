package ru.virarnd.userslistrestapicode61.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("avatar")
    val avatar: String?
) : Parcelable

@Parcelize
data class SingleUserDataResponse (
    @SerializedName("data")
    val user: User
) : Parcelable

@Parcelize
data class UsersListResponse(
    @SerializedName("page")
    val currentPage: Long,
    @SerializedName("per_page")
    val usersPerPage: Long,
    @SerializedName("total")
    val totalUsers: Long,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("data")
    val usersDataList : List<User>
) : Parcelable
package ru.virarnd.userslistrestapicode61.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReqresService {

    @GET("users")
    fun getUsers(@Query("page") page: Long): Call<UsersListResponse>

    @GET("users/{id}")
    fun getSingleUser(@Path("id") id : Long): Call<SingleUserDataResponse>


    companion object Factory{
        fun create(): ReqresService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY;
            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ReqresService::class.java)
        }
    }

}

package com.example.colors.networking

import com.example.colors.models.ColorModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ColorService {

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ColorApi::class.java)

    fun getColors(): Single<List<ColorModel>> {
        return apiService.getColors()
    }
}
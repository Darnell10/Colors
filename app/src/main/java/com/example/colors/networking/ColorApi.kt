package com.example.colors.networking

import com.example.colors.models.ColorModel
import io.reactivex.Single
import retrofit2.http.GET

interface ColorApi {

    @GET("photos")
    fun getColors():Single<List<ColorModel>>

}
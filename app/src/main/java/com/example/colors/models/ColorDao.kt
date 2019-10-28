package com.example.colors.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ColorDao {

    @Insert
    suspend fun insertAll(vararg colors: ColorModel): List<Long>

    @Query("SELECT * FROM colormodel")
    suspend fun getAllColors(): List<ColorModel>

    @Query("SELECT * FROM colormodel WHERE uuid = :colorId")
    suspend fun getColor(colorId: Int): ColorModel

    @Query("DELETE FROM colormodel")
    suspend fun deleteAllColors()

}
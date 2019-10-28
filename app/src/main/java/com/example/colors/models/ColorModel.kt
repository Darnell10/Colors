package com.example.colors.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ColorModel(

    @ColumnInfo(name = "albumId")
    val albumId: String?,

    @ColumnInfo(name = "id")
    val id: String?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "thumbnail")
    val thumbnailUrl: String?


) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

data class ColorPalette(var color: Int)
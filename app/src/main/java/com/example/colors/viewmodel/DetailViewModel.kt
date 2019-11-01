package com.example.colors.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.colors.models.ColorDatabase
import com.example.colors.models.ColorModel
import kotlinx.coroutines.launch
import java.util.*

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val colorLiveData = MutableLiveData<ColorModel>()

    fun fetch(uuid: Int) {
        launch {
            val color: ColorModel = ColorDatabase(getApplication()).colorDao().getColor(uuid)
            colorLiveData.value = color
        }
    }

}


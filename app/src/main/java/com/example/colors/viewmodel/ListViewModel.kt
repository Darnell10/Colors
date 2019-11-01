package com.example.colors.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.colors.models.ColorDatabase
import com.example.colors.models.ColorModel
import com.example.colors.networking.ColorService
import com.example.colors.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPreferenceHelper(getApplication())

    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val colorService = ColorService()

    private val disposable = CompositeDisposable()

    val colors = MutableLiveData<List<ColorModel>>()

    val colorLoadError = MutableLiveData<Boolean>()

    val loading = MutableLiveData<Boolean>()


    fun refresh() {
        val updateTime: Long? = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }


    fun refreshBypassCash() {
        fetchFromRemote()
    }


    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val colors = ColorDatabase(getApplication()).colorDao().getAllColors()
            colorRetrieved(colors)
        }
    }


    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            colorService.getColors()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<ColorModel>>() {

                    override fun onSuccess(colorList: List<ColorModel>) {
                        storeColorLocally(colorList)
                    }

                    override fun onError(e: Throwable) {
                        colorLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }


    private fun colorRetrieved(colorList: List<ColorModel>) {
        colors.value = colorList
        colorLoadError.value = false
        loading.value = false
    }

    private fun storeColorLocally(list: List<ColorModel>) {
        launch {
            val dao = ColorDatabase(getApplication()).colorDao()
            dao.deleteAllColors()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }

            colorRetrieved(list)
        }

        prefHelper.savedUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
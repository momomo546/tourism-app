package com.example.kenroku_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kenroku_app.R
import com.example.kenroku_app.view.fragments.home.MapData

class MapListViewModel : ViewModel() {

    private val _mapList = MutableLiveData<List<MapData>>().apply {
        value = listOf(
            MapData(R.drawable.img_marker_04, R.string.kenrokuen),
            MapData(R.drawable.img_marker_06, R.string.yamanaka_onsen),
        )
    }
    val mapList: LiveData<List<MapData>> = _mapList
}
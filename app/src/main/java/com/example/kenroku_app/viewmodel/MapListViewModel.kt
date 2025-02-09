package com.example.kenroku_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kenroku_app.R
import com.example.kenroku_app.view.fragments.selectMap.MapData

class MapListViewModel : ViewModel() {

    private val _mapList = MutableLiveData<List<MapData>>().apply {
        value = listOf(
            MapData(R.drawable.kenrokuen_img_marker_04, R.string.kenrokuen,"kenrokuen"),
            MapData(R.drawable.kenrokuen_img_marker_06, R.string.yamanaka_onsen,"yamanaka_onsen"),
        )
    }
    val mapList: LiveData<List<MapData>> = _mapList
}
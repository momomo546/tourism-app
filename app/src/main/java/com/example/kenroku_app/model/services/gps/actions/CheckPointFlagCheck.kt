package com.example.kenroku_app.model.services.gps.actions

import android.content.Context
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.AchieveDataStore
import com.example.kenroku_app.model.repositories.data.TouristSpotData.Companion.touristSpotId
import com.example.kenroku_app.model.services.google_map.GoogleMapMarker
import com.google.gson.Gson

class CheckPointFlagCheck(val context: Context) {
    // 変数保存
    private val sharedPreferences = context.getSharedPreferences("checkPointFlag", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val gson = Gson()

    private val AREA = 15
    private var checkPointFlag = MutableList(27) { false }
    private val fileString = "${touristSpotId}_checkPointFlag"
    private val achieveData: AchieveData
        get() = requireNotNull(AchieveDataStore.currentAchieveData) {
            "AchieveDataStore.currentAchieveData が null です。先に load() を呼んでください。"
        }


    init {
        checkPointFlag = achieveData.checkPointFlag.toMutableList()
    }

    fun checkCheckPointFlag(index: Int, distance: Float, googleMapMarker: GoogleMapMarker?) {
        if(distance<AREA) {
            if (googleMapMarker != null && !achieveData.checkPointFlag[index]) {
                googleMapMarker.resetMarker(index)
            }
            checkPointFlag[index] = true
            achieveData.checkPointFlag = checkPointFlag.toList()

            AchieveDataStore.save(context,touristSpotId)
        }
    }
}
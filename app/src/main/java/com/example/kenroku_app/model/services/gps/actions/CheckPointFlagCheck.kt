package com.example.kenroku_app.model.services.gps.actions

import android.content.Context
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.TouristSpotData.Companion.touristSpotId
import com.example.kenroku_app.model.services.google_map.GoogleMapMarker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CheckPointFlagCheck(context: Context) {
    // 変数保存
    private val sharedPreferences = context.getSharedPreferences("checkPointFlag", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val gson = Gson()

    private val AREA = 15
    private var checkPointFlag = MutableList(27) { false }
    private val fileString = "${touristSpotId}_checkPointFlag"

    init {
        val jsonFlag = sharedPreferences.getString(fileString, "")
        checkPointFlag = if (jsonFlag == ""){
            if(touristSpotId=="kenrokuen"){
                MutableList(27){ false }
            }else{
                MutableList(17){ false }
            }

        } else {
            val type = object : TypeToken<MutableList<Boolean>>() {}.type
            gson.fromJson(jsonFlag, type) ?: mutableListOf()
        }
        AchieveData.checkPointFlag = checkPointFlag
    }

    fun checkCheckPointFlag(index: Int, distance: Float, googleMapMarker: GoogleMapMarker?) {
        if(distance<AREA) {
            if (googleMapMarker != null && !AchieveData.checkPointFlag[index]) {
                googleMapMarker.resetMarker(index)
            }
            checkPointFlag[index] = true
            AchieveData.checkPointFlag = checkPointFlag

            val json = gson.toJson(checkPointFlag)
            editor.putString(fileString, json)
            editor.apply()
        }
    }
}
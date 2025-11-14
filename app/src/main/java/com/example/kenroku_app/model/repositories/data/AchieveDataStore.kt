package com.example.kenroku_app.model.repositories.data

import android.content.Context
import android.util.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object AchieveDataStore {
    private const val PREF_NAME = "achieve_data_pref"

    // Companion objectでアプリ内のどこでもアクセス可能にする
    var currentAchieveData: AchieveData? = null

    fun load(context: Context, touristSpotId: String){
        Log.d("AchieveDataStore","LoadAchieveData")
        Log.d("AchieveDataStore",touristSpotId)
        val prefs = context.getSharedPreferences(PREF_NAME+"_"+touristSpotId, Context.MODE_PRIVATE)
        val json = prefs.getString(touristSpotId, null)

        val data = if (json != null) {
            Json.decodeFromString<AchieveData>(json)
        } else {
            createEmptyAchieveData(touristSpotId)
        }

        currentAchieveData = data // companion object に格納
    }

    fun save(context: Context, touristSpotId: String) {
        Log.d("AchieveDataStore","SaveEmptyAchieveData")
        Log.d("AchieveDataStore",touristSpotId)
        if (currentAchieveData == null) return
        val prefs = context.getSharedPreferences(PREF_NAME+"_"+touristSpotId, Context.MODE_PRIVATE)
        val json = Json.encodeToString<AchieveData>(currentAchieveData!!)
        prefs.edit().putString(touristSpotId, json).apply()
    }

    fun createEmptyAchieveData(touristSpotId: String): AchieveData {
        Log.d("AchieveDataStore","createEmptyAchieveData")
        Log.d("AchieveDataStore",touristSpotId)
        var checkPointCount = 0
        if(touristSpotId =="kenrokuen"){
            checkPointCount = 27
        }else if(touristSpotId =="yamanaka_onsen"){
            checkPointCount = 30
        }
        return AchieveData(
            checkPointFlag = List(checkPointCount) { false },
            seasonFlag = List(4) { false },
            steps = 0,
            visitCount = 0,
            calenderDate = 0
        )
    }
}

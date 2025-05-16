package com.example.kenroku_app.model.services.gps.actions

import android.content.Context
import android.util.Log
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.AchieveDataStore
import com.example.kenroku_app.model.repositories.data.TouristSpotData.Companion.touristSpotId
import java.util.Calendar

class SeasonFlagCheck(private val context: Context, private val callback: (String) -> Unit) {
    // 変数保存
    private val sharedPreferences = context.getSharedPreferences("seasonFlag", Context.MODE_PRIVATE)
//    private val editor = sharedPreferences.edit()
//    private val gson = Gson()

    private val fileString = "${touristSpotId}_checkPointFlag"
    private var seasonFlag = MutableList(4){ false }
    private val achieveData: AchieveData
        get() = requireNotNull(AchieveDataStore.currentAchieveData) {
            "AchieveDataStore.currentAchieveData が null です。先に load() を呼んでください。"
        }

    init {
        seasonFlag = achieveData.seasonFlag.toMutableList()
//        val jsonFlag = sharedPreferences.getString(fileString, "")
//        seasonFlag = if (jsonFlag == "") {
//            MutableList(4) { false }
//        } else {
//            val type = object : TypeToken<MutableList<Boolean>>() {}.type
//            gson.fromJson(jsonFlag, type) ?: mutableListOf()
//        }
//        achieveData.seasonFlag = seasonFlag
    }

    private fun getCurrentMonth(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1  // 月は0から始まるため、+1する
    }

    fun checkSeasonFlag() {
        val month = getCurrentMonth()
        if(month in 3..5) {
            if(!achieveData.seasonFlag[0]){
                seasonFlag[0] = true
                callback("春バッジを獲得しました。")
                achieveData.seasonFlag = seasonFlag.toList()
                AchieveDataStore.save(context,touristSpotId)
            }
        }
        else if(month in 6..8) {
            if(!achieveData.seasonFlag[1]){
                seasonFlag[1] = true
                callback("夏バッジを獲得しました。")
                achieveData.seasonFlag = seasonFlag.toList()
                AchieveDataStore.save(context,touristSpotId)
            }
        }
        else if(month in 9..11) {
            if(!achieveData.seasonFlag[2]){
                seasonFlag[2] = true
                callback("秋バッジを獲得しました。")
                achieveData.seasonFlag = seasonFlag.toList()
                AchieveDataStore.save(context,touristSpotId)
            }
        }
        else {
            if(!achieveData.seasonFlag[3]){
                seasonFlag[3] = true
                callback("冬バッジを獲得しました。")
                achieveData.seasonFlag = seasonFlag.toList()
                AchieveDataStore.save(context,touristSpotId)
            }
        }

//        achieveData.seasonFlag = seasonFlag
//        val json = gson.toJson(seasonFlag)
//        editor.putString("seasonFlag", json)
//        editor.apply()
    }

    fun check() {
        Log.d("debug", seasonFlag[0].toString())
        Log.d("debug", seasonFlag[1].toString())
        Log.d("debug", seasonFlag[2].toString())
        Log.d("debug", seasonFlag[3].toString())
    }
}
package com.example.kenroku_app.model.services.gps.actions

import android.content.Context
import android.util.Log
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.AchieveDataStore
import com.example.kenroku_app.model.repositories.data.TouristSpotData.Companion.touristSpotId
import java.util.Calendar

class VisitCount(private val context: Context){
    // 変数保存
    private val sharedPreferences = context.getSharedPreferences("VisitCount_$touristSpotId", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    private var year = 2000
    private var month = 1
    private var date = 1
    private val achieveData: AchieveData
        get() = requireNotNull(AchieveDataStore.currentAchieveData) {
            "AchieveDataStore.currentAchieveData が null です。先に load() を呼んでください。"
        }

    init {
        val calendar = Calendar.getInstance()
        if(achieveData.calenderDate!= 0.toLong()){
            Log.d("AchieveData_VisitCount","init")
            calendar.timeInMillis = achieveData.calenderDate
            year = calendar[Calendar.YEAR]
            month = calendar[Calendar.MONTH]
            date = calendar[Calendar.DATE]
        }
    }

    fun add(){
        if (isVisitCount()) {
            Log.d("AchieveData_VisitCount","add")
            achieveData.visitCount++
            AchieveDataStore.save(context,touristSpotId)
        }
    }

    private fun isVisitCount(): Boolean {
        Log.d("AchieveData_VisitCount","$year/$month/$date")
        val calCheck: Calendar = Calendar.getInstance()
        if (calCheck[Calendar.YEAR]>year) return calenderUpdate(calCheck)
        if (calCheck[Calendar.MONTH]>month) return calenderUpdate(calCheck)
        if (calCheck[Calendar.DATE]>date) return calenderUpdate(calCheck)
        return false
    }

    private fun dateUpdate(calender: Calendar){
        year = calender[Calendar.YEAR]
        month = calender[Calendar.MONTH]
        date = calender[Calendar.DATE]
    }

    private fun calenderUpdate(calender: Calendar):Boolean{
        Log.d("AchieveData_VisitCount","calenderUpdate $calender")

        dateUpdate(calender)

        val timeInMillis = calender.timeInMillis
        achieveData.calenderDate = timeInMillis

        AchieveDataStore.save(context,touristSpotId)

//        editor.putInt("year", year)
//        editor.putInt("month", month)
//        editor.putInt("date", date)
//        editor.apply()

        return true
    }
}
package com.example.kenroku_app.model.services.achievement

import android.content.Context
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.AchieveDataStore
import com.example.kenroku_app.view.fragments.achieve.badge.BadgeData

class AchievementSeason(context: Context, badgeData: List<BadgeData>, private val position:Int) : Achievement(badgeData) {
    private val item = badgeData[position]
    private val achieveData: AchieveData
        get() = requireNotNull(AchieveDataStore.currentAchieveData) {
            "AchieveDataStore.currentAchieveData が null です。先に load() を呼んでください。"
        }
    override fun checkAchievementConditions(){
        if (achieveData.seasonFlag[position]) {
            imageresource = item.imageResId
            text = item.textId
        }
    }
}

class AchievementCheckPoint(context: Context, badgeData: List<BadgeData>, private val position:Int) : Achievement(badgeData) {
    private val item = badgeData[position]
    private val achieveData: AchieveData
        get() = requireNotNull(AchieveDataStore.currentAchieveData) {
            "AchieveDataStore.currentAchieveData が null です。先に load() を呼んでください。"
        }
    override fun checkAchievementConditions(){
        if (achieveData.checkPointFlag[position]) {
            imageresource = item.imageResId
            text = item.textId
        }
    }
}
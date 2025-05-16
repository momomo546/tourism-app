package com.example.kenroku_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.AchieveDataStore

class AchieveViewModel : ViewModel() {

    private val _checkPointText = MutableLiveData<String>().apply {
        value = "This is achieve Fragment"
    }
    private val _walkCountText = MutableLiveData<String>().apply {
        value = "This is achieve Fragment"
    }
    private val _visitCountText = MutableLiveData<String>().apply {
        value = "This is achieve Fragment"
    }
    private val achieveData: AchieveData
        get() = requireNotNull(AchieveDataStore.currentAchieveData) {
            "AchieveDataStore.currentAchieveData が null です。先に load() を呼んでください。"
        }


    val checkPointText: LiveData<String> = _checkPointText
    val walkCountText: LiveData<String> = _walkCountText
    val visitCountText: LiveData<String> = _visitCountText

    fun viewUpdate(){
        val listSize = achieveData.checkPointFlag.size
        val trueCount = achieveData.checkPointFlag.count { it }
        _checkPointText.value = "$trueCount/$listSize"

        val variableValue = achieveData.steps
        _walkCountText.value = "$variableValue"

        val visitCount = achieveData.visitCount
        _visitCountText.value = "$visitCount"
    }
}
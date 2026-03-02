package com.example.kenroku_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.AchieveDataStore

class PointsViewModel : ViewModel() {
    private val _points1 = MutableLiveData<Int>()
    val points1: LiveData<Int> get() = _points1
    private val _points1Total = MutableLiveData<Int>()
    val points1Total: LiveData<Int> get() = _points1Total

    private val _points2 = MutableLiveData<Int>()
    val points2: LiveData<Int> get() = _points2
    private val _points2Total = MutableLiveData<Int>()
    val points2Total: LiveData<Int> get() = _points2Total

    private val _points3 = MutableLiveData<Int>()
    val points3: LiveData<Int> get() = _points3
    private val _points3Total = MutableLiveData<Int>()
    val points3Total: LiveData<Int> get() = _points3Total

    private val _rankText = MutableLiveData<String>()
    val rankText: LiveData<String> get() = _rankText

    private val achieveData: AchieveData
        get() = requireNotNull(AchieveDataStore.currentAchieveData) {
            "AchieveDataStore.currentAchieveData が null です。先に load() を呼んでください。"
        }

    private val scoreDistribution: Map<Int, Int> = mapOf(
        0 to 0,
        1 to 25,
        2 to 40,
        3 to 50,
        4 to 35,
        5 to 60,
        6 to 55,
        7 to 45,
        8 to 40,
        9 to 30,
        10 to 25,
        11 to 20,
        12 to 18,
        13 to 20,
        14 to 20,
        15 to 20,
        16 to 15,
        17 to 15,
        18 to 15,
        19 to 15,
        20 to 15,
        21 to 10,
        22 to 10,
        23 to 10,
        24 to 10,
        25 to 10,
        26 to 10,
        27 to 10,
        28 to 10,
        29 to 8,
        30 to 5,
        31 to 3,
        32 to 2
    )

    companion object {
        val points1List = listOf(6,19,20,21,22,23,24,26,29)
        val points2List = listOf(1, 2, 4, 8, 9, 10, 11, 13,15,16,18,25,27,28)
        val points3List = listOf(0, 3, 5, 7, 12,14,17)
//        val points1List = listOf(100)
//        val points2List = listOf(1, 2, 4, 8, 9, 10, 11, 13,15,16,18,25,27,28,6,19,20,21,22,23,24,26,29,0, 3, 5, 7, 12,14,17)
//        val points3List = listOf(101)
    }

    // 例: flagはBoolのList、ポイントグループはIntのList（獲得フラグのインデックス）
    fun viewUpdate() {
        val flag: List<Boolean> = achieveData.checkPointFlag

        // 獲得した個数をカウントする関数
        fun countAcquired(pointsIndices: List<Int>): Int {
            // flagリストのサイズ超過に注意しつつカウント
            return pointsIndices.count { index -> index < flag.size && flag[index] }
        }

        // 現在値 = 獲得数, 合計値 = pointsListのサイズ
        val currentPoints1 = countAcquired(points1List)
        val totalPoints1 = points1List.size
        setPoints1(currentPoints1, totalPoints1)

        val currentPoints2 = countAcquired(points2List)
        val totalPoints2 = points2List.size
        setPoints2(currentPoints2, totalPoints2)

        val currentPoints3 = countAcquired(points3List)
        val totalPoints3 = points3List.size
        setPoints3(currentPoints3, totalPoints3)

        // 合計点を算出
        val totalScore = currentPoints1 + currentPoints2 + currentPoints3

        // 上位％を計算
        updateRank(totalScore)
    }

    private fun updateRank(myScore: Int) {
        if (myScore == 0) {
            _rankText.value = "まだスタートライン！まずは1点を目指そう🔥"
            return
        }

        // 総人数
        val totalPlayers = scoreDistribution.values.sum()

        // 自分より低い点数の人数
        val belowMe = scoreDistribution
            .filterKeys { it < myScore }
            .values.sum()

        // 上位% = (自分より下の人数 + 同点の半分) / 総人数
        val sameScore = scoreDistribution[myScore] ?: 0
        val myPercent = (belowMe + sameScore / 2.0) / totalPlayers * 100

        _rankText.value = "あなたは上位 ${"%.1f".format(100 - myPercent)}%の得点です！"
//        _rankText.value = "xxx"
    }

    // 値セット用関数
    fun setPoints1(current: Int, total: Int) {
        _points1.value = current
        _points1Total.value = total
    }
    fun setPoints2(current: Int, total: Int) {
        _points2.value = current
        _points2Total.value = total
    }
    fun setPoints3(current: Int, total: Int) {
        _points3.value = current
        _points3Total.value = total
    }
}

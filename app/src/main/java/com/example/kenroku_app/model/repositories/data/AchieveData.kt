package com.example.kenroku_app.model.repositories.data

import kotlinx.serialization.Serializable

@Serializable
data class AchieveData(
    var checkPointFlag: List<Boolean>,
    var seasonFlag: List<Boolean> = List(4) { false },
    var steps: Int = 0,
    var visitCount: Int = 0
)

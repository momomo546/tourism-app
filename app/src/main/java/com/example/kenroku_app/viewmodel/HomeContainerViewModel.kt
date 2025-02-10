package com.example.kenroku_app.viewmodel

import androidx.lifecycle.ViewModel

class HomeContainerViewModel : ViewModel() {
    enum class HomeState {
        TOURIST_SELECTION, MAIN, DETAIL
    }

    var currentState: HomeState = HomeState.TOURIST_SELECTION
}

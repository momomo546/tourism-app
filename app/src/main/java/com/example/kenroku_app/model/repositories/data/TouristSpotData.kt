package com.example.kenroku_app.model.repositories.data

import com.google.android.gms.maps.model.LatLng

class TouristSpotData {
    companion object {
        var touristSpotId: String = "kenrokuen"
        var centerLatLng: LatLng = LatLng(0.0,0.0)
        var radius: Double = 1000.0
    }
}

package com.example.kenroku_app.model.services.gps.actions

import android.location.Location
import com.example.kenroku_app.model.repositories.data.TouristSpotData

class LocationCheck(){
    private var targetLocation: Location = Location("")

    init{
        targetLocation.latitude = TouristSpotData.centerLatLng.latitude
        targetLocation.longitude = TouristSpotData.centerLatLng.longitude
    }

    fun isWithinRange(userLocation: Location): Boolean {
//        val targetLocation = Location("target")
        val targetLocation = Location("")
        targetLocation.latitude = TouristSpotData.centerLatLng.latitude
        targetLocation.longitude = TouristSpotData.centerLatLng.longitude
//        val radius = 262
        val radius = TouristSpotData.radius
        val distance = userLocation.distanceTo(targetLocation)

        return distance <= radius
    }
}
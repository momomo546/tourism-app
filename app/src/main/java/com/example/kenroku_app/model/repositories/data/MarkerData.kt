package com.example.kenroku_app.model.repositories.data

import com.example.kenroku_app.model.services.google_map.GoogleMapMarker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Vector

class MarkerData {
    companion object {
        var markerPosition: Vector<LatLng> = Vector()
        var markerOptionList: Vector<MarkerOptions> = Vector()

        var googleMapMarker: GoogleMapMarker? = null
    }
}
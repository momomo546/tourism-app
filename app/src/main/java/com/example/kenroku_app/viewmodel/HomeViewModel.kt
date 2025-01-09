package com.example.kenroku_app.viewmodel

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kenroku_app.R
import com.example.kenroku_app.model.repositories.data.TouristSpotData
import com.example.kenroku_app.model.services.google_map.GoogleMapPolyline
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class HomeViewModel : ViewModel() {

    private lateinit var mMap: GoogleMap

    private lateinit var _mapConfig: JSONObject

    // 指定された観光地IDに基づいて設定を読み込む
    fun loadMapConfig(assetManager: AssetManager): JSONObject {
        val fileName = "${TouristSpotData.touristSpotId}/map_setting.json"
        try {
            val inputStream = assetManager.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            return JSONObject(jsonString)
        } catch (e: IOException) {
            Log.e("loadMapConfig", "ファイルが見つかりません: $fileName", e)
            return JSONObject()
        }
    }
    fun loadMapStyle(assetManager: AssetManager): JSONArray {
        val inputStream = assetManager.open("${TouristSpotData.touristSpotId}/map_style.json")
        val style = inputStream.bufferedReader().use { it.readText() }
        return JSONArray(style)
    }

    fun initializeMap(googleMap: GoogleMap) {
        mMap = googleMap

        // ズームレベルの制限
        val zoomLimits = _mapConfig.getJSONObject("zoomLimits")
        mMap.setMinZoomPreference(zoomLimits.getDouble("minZoom").toFloat())
        mMap.setMaxZoomPreference(zoomLimits.getDouble("maxZoom").toFloat())

        // 移動範囲の制限
        val mapBounds = _mapConfig.getJSONObject("mapBounds")
        val southwest = mapBounds.getJSONObject("southwest")
        val northeast = mapBounds.getJSONObject("northeast")
        TouristSpotData.radius = mapBounds.getDouble("radius")
        val bounds = LatLngBounds(
            LatLng(southwest.getDouble("latitude"), southwest.getDouble("longitude")),
            LatLng(northeast.getDouble("latitude"), northeast.getDouble("longitude"))
        )
        setMapBounds(bounds)

        addOverlaysAndCamera()
        addKenrokuenMarker()
        mMap.uiSettings.isZoomControlsEnabled = true

        val googleMapPolyline = GoogleMapPolyline(mMap)
        googleMapPolyline.setPolyline()
    }

    private fun setMapBounds(latLngBounds: LatLngBounds) {
        // 移動の制限の適用
        mMap.setLatLngBoundsForCameraTarget(latLngBounds)
    }

    private fun addOverlaysAndCamera() {
        // 初期位置設定
        val initialLocation = _mapConfig.getJSONObject("initialLocation")
        val lat = initialLocation.getDouble("latitude")
        val lng = initialLocation.getDouble("longitude")
        TouristSpotData.centerLatLng = LatLng(lat,lng)
        val zoom = initialLocation.getDouble("zoom").toFloat()
        setInitialCameraPosition(LatLng(lat, lng),zoom)

        if (_mapConfig.has("backgroundOverlayImage")) {
            val backgroundOverlayConfig = _mapConfig.getJSONObject("backgroundOverlayImage")
            //今後API作成したとき用
            val backgroundOverlayUrl = backgroundOverlayConfig.getString("url")
            val backgroundOverlayPosition = backgroundOverlayConfig.getJSONObject("position")
            val backgroundOverlayLat = backgroundOverlayPosition.getDouble("latitude")
            val backgroundOverlayLng = backgroundOverlayPosition.getDouble("longitude")
            val backgroundOverlayWidth = backgroundOverlayConfig.getInt("width").toFloat()
            val backgroundOverlayHeight = backgroundOverlayConfig.getInt("height").toFloat()
            val backgroundOverlayLatLng = LatLng(backgroundOverlayLat, backgroundOverlayLng)

            //背景
            val backgroundOverlayMap = GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.white))
                .position(backgroundOverlayLatLng, backgroundOverlayWidth, backgroundOverlayHeight)
            mMap.addGroundOverlay(backgroundOverlayMap)
        }

        if (_mapConfig.has("overlayImage")) {
            val overlayConfig = _mapConfig.getJSONObject("overlayImage")
            //今後API作成したとき用
            val overlayUrl = overlayConfig.getString("url")
            val overlayPosition = overlayConfig.getJSONObject("position")
            val overlayLat = overlayPosition.getDouble("latitude")
            val overlayLng = overlayPosition.getDouble("longitude")
            val overlayWidth = overlayConfig.getInt("width").toFloat()
            val overlayHeight = overlayConfig.getInt("height").toFloat()
            val overlayLatLng = LatLng(overlayLat, overlayLng)

            //地図
            val overlayMap = GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.map_kenrokuen))
                .position(overlayLatLng, overlayWidth, overlayHeight)

            mMap.addGroundOverlay(overlayMap)
        }
    }

    private fun setInitialCameraPosition(latLng: LatLng, zoom: Float) {
        //カメラとズームの初期位置設定
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom))
    }
    private fun addKenrokuenMarker() {

    }

    fun setMapConfig(mapConfig: JSONObject) {
        _mapConfig = mapConfig
    }
}
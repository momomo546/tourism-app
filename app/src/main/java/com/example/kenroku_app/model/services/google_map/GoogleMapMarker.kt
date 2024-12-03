package com.example.kenroku_app.model.services.google_map

import android.content.Context
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import com.example.kenroku_app.R
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.MarkerData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class GoogleMapMarker(
    val context: Context,
    val mMap: GoogleMap,
    touristSpotId: String,
    assetManager: AssetManager
) {
    //表示されているマーカーのマップ
    private var addMarkerMap: MutableMap<Int,Marker?> = mutableMapOf()

    init{
        loadMarkerConfig(touristSpotId,assetManager)
    }

    fun loadMarkerConfig(touristSpotId: String, assetManager: AssetManager){
        val inputStream = try {
            // touristSpotIdに基づいてファイルパスを生成し、JSONファイルを開く
            assetManager.open("$touristSpotId/marker_list.json")
        } catch (e: IOException) {
            Log.e("GoogleMapMarker", "ファイルが見つかりません: $touristSpotId/marker_list.json", e)
            null  // ファイルがない場合はnullを返す
        }
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val str: String = bufferedReader.readText() //データ

        val jsonObject = JSONObject(str)
        val jsonArray = jsonObject.getJSONArray("markerList")

        for (i in 0 until jsonArray.length()) {
            val jsonData = jsonArray.getJSONObject(i)
            val latLng = LatLng(jsonData.getDouble("lat"),jsonData.getDouble("lng"))
            val name = jsonData.getString("name")
            //val iconColor = jsonData.getString("icon")
            val resourceText = context.resources.getIdentifier("${touristSpotId}_${name}", "string", context.packageName)
            MarkerData.markerPosition.add(latLng)
            MarkerData.markerOptionList.add(
                MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(context.getString(resourceText))
            )
        }
        bufferedReader.close()
    }

    fun addMarker(){
        for ((index, value) in MarkerData.markerOptionList.withIndex()) {
            if (AchieveData.checkPointFlag[index]) value.icon(
                BitmapDescriptorFactory.fromResource(
                    R.drawable.check_mark
                )
            )
            val formattedIndex = String.format("%02d", index + 1)
            val marker = mMap.addMarker(value)
            marker?.tag = "marker_${formattedIndex}"
            addMarkerMap[index]=marker
        }
    }

    fun removeMarker(index:Int){
        addMarkerMap[index]?.remove()
    }

    fun resetMarker(index:Int){
        MarkerData.markerOptionList[index].icon(BitmapDescriptorFactory.fromResource(R.drawable.check_mark))
        addMarkerMap[index] = mMap.addMarker(MarkerData.markerOptionList[index])
        val mediaPlayer = MediaPlayer.create(context, R.raw.rappa)
        mediaPlayer.start()
        val toast = Toast.makeText(context, MarkerData.markerOptionList[index].title+"を通過しました！", Toast.LENGTH_LONG)
        toast.show()
    }
}
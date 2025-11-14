package com.example.kenroku_app.model.services.google_map

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import com.example.kenroku_app.R
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.AchieveDataStore
import com.example.kenroku_app.model.repositories.data.MarkerData
import com.example.kenroku_app.viewmodel.PointsViewModel
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
    val touristSpotId: String,
    assetManager: AssetManager
) {
    //表示されているマーカーのマップ
    private var addMarkerMap: MutableMap<Int,Marker?> = mutableMapOf()
    private val achieveData: AchieveData
        get() = requireNotNull(AchieveDataStore.currentAchieveData) {
            "AchieveDataStore.currentAchieveData が null です。先に load() を呼んでください。"
        }

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
            val latLng = LatLng(jsonData.getDouble("lat"), jsonData.getDouble("lng"))
            val name = jsonData.getString("name")

            val resourceText = context.resources.getIdentifier(
                "${touristSpotId}_${name}",
                "string",
                context.packageName
            )

            try {
                println(context.getString(resourceText))
            } catch (e: Resources.NotFoundException) {
                Log.e("ResourceError", "リソースが見つかりません: ${touristSpotId}_${name}", e)
            }

            var hue = BitmapDescriptorFactory.HUE_RED
            // マーカー色をリストに応じて決定
            if (touristSpotId=="yamanaka_onsen") {
                hue = when (i) {
                    in PointsViewModel.points1List -> BitmapDescriptorFactory.HUE_GREEN
                    in PointsViewModel.points2List -> BitmapDescriptorFactory.HUE_ORANGE
                    in PointsViewModel.points3List -> BitmapDescriptorFactory.HUE_RED
                    else -> BitmapDescriptorFactory.HUE_BLUE // デフォルト
                }
            }

            // マーカーを追加
            MarkerData.markerPosition.add(latLng)
            MarkerData.markerOptionList.add(
                MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(hue))
                    .title(context.getString(resourceText))
            )
        }
        bufferedReader.close()
    }

    fun addMarker(){
        for ((index, value) in MarkerData.markerOptionList.withIndex()) {
            if (achieveData.checkPointFlag[index]) value.icon(
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

    fun resetMarker(index:Int){
        addMarkerMap[index]?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.check_mark))
        val mediaPlayer = MediaPlayer.create(context, R.raw.rappa)
        mediaPlayer.start()
        val toast = Toast.makeText(context, MarkerData.markerOptionList[index].title+"を通過しました！", Toast.LENGTH_LONG)
        toast.show()
    }
}
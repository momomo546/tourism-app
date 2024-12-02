package com.example.kenroku_app.view.fragments.home

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.kenroku_app.R
import com.example.kenroku_app.model.repositories.data.MarkerData
import com.example.kenroku_app.model.services.google_map.GoogleMapMarker
import com.example.kenroku_app.model.services.google_map.MarkerDetailFragment
import com.example.kenroku_app.view.activities.MainActivity
import com.example.kenroku_app.viewmodel.HomeViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class HomeFragment : Fragment(), OnMapReadyCallback{
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var mMap: GoogleMap
    private val TAG: String = MainActivity::class.java.simpleName
    private var isStart = false
    private lateinit var googleMapMarker: GoogleMapMarker

    // 指定された観光地IDに基づいて設定を読み込む
    private fun loadMapConfig(assetManager: AssetManager, id: String): JSONObject {
        val fileName = "$id/map_setting.json"
       try {
            val inputStream = assetManager.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            return JSONObject(jsonString)
        } catch (e: IOException) {
            Log.e("loadMapConfig", "ファイルが見つかりません: $fileName", e)
            return JSONObject()
        }
    }
    private fun loadMapStyle(assetManager: AssetManager, id: String): JSONArray{
        val inputStream = assetManager.open("$id/map_style.json")
        val style = inputStream.bufferedReader().use { it.readText() }
        return JSONArray(style)
    }


    // Fragmentで表示するViewを作成するメソッド
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // 先ほどのレイアウトをここでViewとして作成します
        Log.d("debug", "onCreateView")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        Log.d("debug", "onViewCreated")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (isStart) return
        isStart = true
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        }

        //後でまえのFragmentからIDを受け取るように変更
        val touristSpotId = "yamanaka_onsen"
        CoroutineScope(Dispatchers.IO).launch {
            val assetManager = requireContext().assets
            val mapConfig = loadMapConfig(assetManager, touristSpotId)
            googleMapMarker = GoogleMapMarker(requireContext(), mMap, touristSpotId, assetManager)
            val mapStyle = loadMapStyle(assetManager,touristSpotId)
            withContext(Dispatchers.Main) {
                val success = mMap.setMapStyle(MapStyleOptions(mapStyle.toString()))

                if (!success) {
                    Log.e("MapStyle", "スタイルの適用に失敗しました。")
                }
                homeViewModel.setMapConfig(mapConfig)
                homeViewModel.initializeMap(googleMap)

                googleMapMarker.addMarker()
                MarkerData.googleMapMarker = googleMapMarker
            }
        }

        // 情報ウィンドウの設定
        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }
            override fun getInfoContents(marker: Marker): View? {
                // マーカーをクリックしたときの内容を設定
                val customInfoWindow = layoutInflater.inflate(R.layout.custom_info_window, null)

                val titleTextView = customInfoWindow.findViewById<TextView>(R.id.titleTextView)
                val detailButton = customInfoWindow.findViewById<Button>(R.id.detailButton)

                titleTextView.text = marker.title

                /*detailButton.setOnClickListener {
                    val markerId = marker.tag as? String

                    val bundle = Bundle()
                    bundle.putString("id", markerId)

                    val receivingFragment = MarkerDetailFragment()
                    receivingFragment.arguments = bundle

                    // NavControllerを取得
                    val navController = view?.findNavController()

                    // 画面遷移
                    navController?.navigate(R.id.action_navigation_home_to_markerDetailFragment, bundle)
                }*/

                mMap.setOnInfoWindowClickListener {
                    val markerId = marker.tag as? String

                    val bundle = Bundle()
                    bundle.putString("id", markerId)

                    val receivingFragment = MarkerDetailFragment()
                    receivingFragment.arguments = bundle

                    // NavControllerを取得
                    val navController = view?.findNavController()

                    // 画面遷移
                    navController?.navigate(R.id.action_navigation_home_to_markerDetailFragment, bundle)
                }

                return customInfoWindow
            }
        })
    }
}

package com.example.kenroku_app.model.services.step_counter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.kenroku_app.model.repositories.data.AchieveData
import com.example.kenroku_app.model.repositories.data.AchieveDataStore
import com.example.kenroku_app.model.repositories.data.TouristSpotData.Companion.touristSpotId
import com.example.kenroku_app.viewmodel.activity.MainViewModel

class StepCounter(
    val context: Context,
    private val viewModel: MainViewModel,
    private val onStepDetected: (Int) -> Unit
) : SensorEventListener {

    private var mSensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var mStepDetectorSensor: Sensor? = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
    private var mStepConterSensor: Sensor? = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

//    private val sharedPreferences = context.getSharedPreferences("padometor", Context.MODE_PRIVATE)
//    private val editor = sharedPreferences.edit()
//    private var steps = sharedPreferences.getInt("${touristSpotId}_step", 0)
    private val achieveData: AchieveData
        get() = requireNotNull(AchieveDataStore.currentAchieveData) {
            "AchieveDataStore.currentAchieveData が null です。先に load() を呼んでください。"
        }


    init {
        if (mStepDetectorSensor == null) {
            Log.e("StepCounter", "Step Detector Sensor is not available!")
        }
        if (mStepConterSensor == null) {
            Log.e("StepCounter", "Step Counter Sensor is not available!")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(!viewModel.isLocation) return
        val sensor = event.sensor
        val values = event.values
        //TYPE_STEP_COUNTER
        if (sensor.type == Sensor.TYPE_STEP_COUNTER) {
            // sensor からの値を取得するなどの処理を行う
            Log.d("type_step_counter", values[0].toString())
        }
        achieveData.steps++
        AchieveDataStore.save(context,touristSpotId)
        onStepDetected(achieveData.steps)
    }

    fun registerStepCounterListener() {
        mStepConterSensor?.let {
            mSensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        mStepDetectorSensor?.let {
            mSensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    fun unregisterStepCounterListener(){
        mStepConterSensor?.let {
            mSensorManager.unregisterListener(this, it)
        }
        mStepDetectorSensor?.let {
            mSensorManager.unregisterListener(this, it)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}
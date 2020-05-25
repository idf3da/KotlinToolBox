package com.trinity.toolbox

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sensor.*

class SensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var bundle: Bundle? = null
    private var type: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)
        bundle = intent.extras
        type = bundle!!["sensor_type"] as Int
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if(type != null)sensor = sensorManager.getDefaultSensor(type!!)
    }

    override fun onResume() {
        super.onResume()
        if(type != null)sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    override fun onSensorChanged(event: SensorEvent?) {
        when(event!!.sensor.type){
            type!! -> {
                textView2.text = event.values[0].toString()
                for(i in 1 until event.values.size)
                    textView2.text = "${textView2.text}\n${event.values[i]}"
            }
        }
    }
}

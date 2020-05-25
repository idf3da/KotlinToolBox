package com.trinity.toolbox

import android.content.Context
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sensors.*

class SensorsActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var sensors: MutableList<Sensor>? = null
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }
    override fun onSensorChanged(p0: SensorEvent?) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        textView.text = ""
        for (sensor in sensors!!) {
            textView.text = "${textView.text}name = ${sensor.name}\n"
            textView.text = "${textView.text}type = ${sensor.type}\n"
            textView.text = "${textView.text}vendor = ${sensor.vendor}\n"
            textView.text = "${textView.text}version = ${sensor.version}\n"
            textView.text = "${textView.text}max = ${sensor.maximumRange}\n"
            textView.text = "${textView.text}resolution = ${sensor.resolution}\n\n"
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
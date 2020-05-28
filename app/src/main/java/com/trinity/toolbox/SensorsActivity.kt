package com.trinity.toolbox

import android.content.Context
import android.content.Intent
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        val adapter = SensorItemAdapter(this, sensors!!)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            if(sensors!![position].type <= 20){
                val i = Intent(this, SensorActivity::class.java)
                i.putExtra("sensor_type", sensors!![position].type)
                startActivity(i)
            } else {
                Toast.makeText(this, "Увы, данный тип датчиков не поддерживается", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}

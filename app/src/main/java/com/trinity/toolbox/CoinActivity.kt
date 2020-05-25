package com.trinity.toolbox

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_coin.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class CoinActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        imageView.setOnClickListener {
            imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.coin_anim))
            imageView.postOnAnimation { imageView.isActivated = Random.nextBoolean() }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    override fun onSensorChanged(event: SensorEvent) {
        if(sqrt(event.values[1].pow(2 ) * 1.0 + event.values[2].pow(2 ) * 1.0) * 0.177 >= 4){
            imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.coin_anim))
            imageView.postOnAnimation { imageView.isActivated = Random.nextBoolean() }
        }
    }
}

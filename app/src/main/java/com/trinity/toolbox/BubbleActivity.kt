package com.trinity.toolbox

import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlin.math.round

class BubbleActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(3)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent) {
        when(p0.sensor.type){
            3 -> draw(p0.values[2])
        }
    }

    private fun draw(_value: Float){
        var value = round(_value * 100) / 100
        val imageView: ImageView = findViewById(R.id.imageView)
        val bitmap: Bitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint1 = Paint(); paint1.color = Color.rgb(135, 231, 133); paint1.strokeWidth = 4F
        val paint2 = Paint(); paint2.color = Color.BLACK; paint2.strokeWidth = 6F; paint2.textSize = 32F; paint2.style = Paint.Style.FILL
        val paint3 = Paint(); paint3.color = Color.rgb(223, 255, 208); paint3.strokeWidth = 4F

        canvas.drawRect(RectF(0F, imageView.height * 0.5F, imageView.width.toFloat(), imageView.height * 0.75F), paint1)

        canvas.drawCircle(imageView.width / 2F + imageView.width / 180F * value, imageView.height * 0.625F, imageView.height / 10F, paint3)
        canvas.drawText(value.toString(), imageView.width / 2F + imageView.width / 180F * value - value.toString().length * 6.5F, imageView.height * 0.625F + 8F, paint2)

        canvas.drawLine(imageView.width / 3F, imageView.height * 0.5F, imageView.width / 3F, imageView.height * 0.75F, paint2)
        canvas.drawLine(imageView.width * 2F / 3F, imageView.height * 0.5F, imageView.width * 2F / 3F, imageView.height * 0.75F, paint2)
        canvas.drawLine(0F, imageView.height * 0.5F, imageView.width * 1F, imageView.height * 0.5F, paint2)
        canvas.drawLine(0F, imageView.height * 0.75F, imageView.width * 1F, imageView.height * 0.75F, paint2)
        canvas.drawLine(0F, imageView.height * 0.5F, 0F, imageView.height * 0.75F, paint2)
        canvas.drawLine(imageView.width.toFloat(), imageView.height * 0.5F, imageView.width.toFloat(), imageView.height * 0.75F, paint2)

        imageView.setImageBitmap(bitmap)
    }
}
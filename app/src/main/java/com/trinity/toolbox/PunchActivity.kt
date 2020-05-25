package com.trinity.toolbox

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_punch.*
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt

infix fun <T : Any> Boolean.t(value: T): T? = if(this) value else null

fun PPower(a: FloatArray): Float {
    return (sqrt(a[0].pow(2) + a[1].pow(2) + a[2].pow(2)) * 0.177).toFloat()
}

class PunchActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private var acls: Sensor? = null
    var decimalFormat: DecimalFormat = DecimalFormat("0.00000")
    var i = 0
    var maxN: Float = (0.0).toFloat()
    var switch = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch)

        mediaPlayer = MediaPlayer.create(this, R.raw.beep)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acls = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        startButton = findViewById(R.id.start)
        resetButton = findViewById(R.id.reset)


        startButton.setOnClickListener {
            switch = !switch
            start.text = switch t "Stop recording" ?: "Start recording"
        }
        resetButton.setOnClickListener {
            listView.text = ""
            score.text = "Best score: 0"
            maxN = (0.0).toFloat()
        }
    }

    override fun onResume() {
        super.onResume()
        acls?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        textView.text = "Sensor accuracy: "
        when (accuracy) {
            0 -> { textView.text = "${textView.text}Unreliable" }
            1 -> { textView.text = "${textView.text}Low Accuracy" }
            2 -> { textView.text = "${textView.text}Medium Accuracy" }
            3 -> { textView.text = "${textView.text}High Accuracy" }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (switch) {
            var result = PPower(event.values)

            i++
            listView.text = "${decimalFormat.format(result)}\n${listView.text}"

            if (i % 50 == 0){
                listView.text = ""
            }
            if (result > maxN) {
                maxN = result
                score.text = "Best score: ${maxN}"
            }
            if (result > 7) {
                mediaPlayer?.start()
            }
        }
    }
}


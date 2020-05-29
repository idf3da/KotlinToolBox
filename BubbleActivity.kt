package com.trinity.toolbox

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import kotlin.math.round

class BubbleActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    private var backgroundColor = Color.WHITE
    private var textColor = Color.BLACK
    private var bubbleColor = Color.rgb(223, 255, 208)
    private var instrumentColor = Color.rgb(135, 231, 133)

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
        val paint1 = Paint(); paint1.color = instrumentColor; paint1.strokeWidth = 4F
        val paint2 = Paint(); paint2.color = textColor; paint2.strokeWidth = 6F; paint2.textSize = 32F; paint2.style = Paint.Style.FILL
        val paint3 = Paint(); paint3.color = bubbleColor; paint3.strokeWidth = 4F

        canvas.drawColor(backgroundColor)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val settings = menu?.addSubMenu(0, -1, 0, "Настройки")

        val backgroundColorSM = settings?.addSubMenu(1, -2,0,"Цвет Фона")

        backgroundColorSM?.add(2, 0, 0, "Красный")
        backgroundColorSM?.add(2, 1, 1, "Зеленый")
        backgroundColorSM?.add(2, 2, 2, "Синий")
        backgroundColorSM?.add(2, 3, 3, "Оранжевый")
        backgroundColorSM?.add(2, 4, 4, "Чёрный")
        backgroundColorSM?.add(2, 5, 5, "Белый")

        val bubbleColorSM = settings?.addSubMenu(1, -3,1,"Цвет пузырька")

        bubbleColorSM?.add(3, 6, 0, "Оливковый")
        bubbleColorSM?.add(3, 7, 1, "Темно-оливковый")
        bubbleColorSM?.add(3, 8, 2, "Светло-оливковый")
        bubbleColorSM?.add(3, 9, 3, "Оранжевый")
        bubbleColorSM?.add(3, 10, 4, "Чёрный")
        bubbleColorSM?.add(3, 11, 5, "Белый")

        val textColorSM = settings?.addSubMenu(1, -4,2,"Цвет текста")

        textColorSM?.add(4, 12, 0, "Красный")
        textColorSM?.add(4, 13, 1, "Зеленый")
        textColorSM?.add(4, 14, 2, "Синий")
        textColorSM?.add(4, 15, 3, "Оранжевый")
        textColorSM?.add(4, 16, 4, "Чёрный")
        textColorSM?.add(4, 17, 5, "Белый")

        val instrColorSM = settings?.addSubMenu(1, -5,3,"Цвет инструмента")

        instrColorSM?.add(5, 18, 0, "Оливковый")
        instrColorSM?.add(5, 19, 1, "Темно-оливковый")
        instrColorSM?.add(5, 20, 2, "Светло-оливковый")
        instrColorSM?.add(5, 21, 3, "Оранжевый")
        instrColorSM?.add(5, 22, 4, "Чёрный")
        instrColorSM?.add(5, 23, 5, "Белый")

        menu?.add(0, -6, 0, "Инструкция")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            -6 -> {
                val d = BubbleInstructionDialogFragment()
                val m = supportFragmentManager
                d.show(m, "dialog_bubble_instruction")
            }

            0 -> backgroundColor = Color.RED
            1 -> backgroundColor = Color.GREEN
            2 -> backgroundColor = Color.BLUE
            3 -> backgroundColor = Color.rgb(204, 102, 0)
            4 -> backgroundColor = Color.BLACK
            5 -> backgroundColor = Color.WHITE

            6 -> bubbleColor = Color.rgb(179, 243, 171)
            7 -> bubbleColor = Color.rgb(135, 231, 133)
            8 -> bubbleColor = Color.rgb(223, 255, 208)
            9 -> bubbleColor = Color.rgb(204, 102, 0)
            10 -> bubbleColor = Color.BLACK
            11 -> bubbleColor = Color.WHITE

            12 -> textColor = Color.RED
            13 -> textColor = Color.GREEN
            14 -> textColor = Color.BLUE
            15 -> textColor = Color.rgb(204, 102, 0)
            16 -> textColor = Color.BLACK
            17 -> textColor = Color.WHITE

            18 -> instrumentColor = Color.rgb(179, 243, 171)
            19 -> instrumentColor = Color.rgb(135, 231, 133)
            20 -> instrumentColor = Color.rgb(223, 255, 208)
            21 -> instrumentColor = Color.rgb(204, 102, 0)
            22 -> instrumentColor = Color.BLACK
            23 -> instrumentColor = Color.WHITE

            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}

class BubbleInstructionDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Инструкция")
                .setMessage("\tДля того, чтобы измерить угол поверхности, переведите телефон в вертикальный режим и перпендикулярно приложите его к поверхности. \nЧисло, заключённое в круг, укажет на кол-во градусов, на которое угол отличается от нормы(0).")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("ОК") {
                        dialog, _ ->  dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
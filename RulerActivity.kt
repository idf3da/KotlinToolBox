package com.trinity.toolbox

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.*
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class RulerActivity : AppCompatActivity() {
    private var rulerColor = Color.rgb(204, 102, 0)
    private var textColor = Color.WHITE
    private var backgroundColor = Color.WHITE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruler)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        draw()
        val settings = menu.addSubMenu(0, -1, 0,"Настройки")
        val setRulerColor = settings.addSubMenu(0, -2, 0,"Цвет линейки")
        setRulerColor.add(1, 0, 0, "Красный")
        setRulerColor.add(1, 1, 1, "Зеленый")
        setRulerColor.add(1, 2, 2, "Синий")
        setRulerColor.add(1, 3, 3, "Оранжевый")
        setRulerColor.add(1, 4, 4, "Чёрный")
        setRulerColor.add(1, 5, 5, "Белый")
        val setTextColor = settings.addSubMenu(0, -3, 1, "Цвет текста")
        setTextColor.add(2, 6, 0, "Красный")
        setTextColor.add(2, 7, 1, "Зеленый")
        setTextColor.add(2, 8, 2, "Синий")
        setTextColor.add(2, 9, 3, "Оранжевый")
        setTextColor.add(2, 10, 4, "Чёрный")
        setTextColor.add(2, 11, 5, "Белый")
        val setBackgroundColor = settings.addSubMenu(0, -4, 2, "Цвет фона")
        setBackgroundColor.add(2, 12, 0, "Красный")
        setBackgroundColor.add(2, 13, 1, "Зеленый")
        setBackgroundColor.add(2, 14, 2, "Синий")
        setBackgroundColor.add(2, 15, 3, "Оранжевый")
        setBackgroundColor.add(2, 16, 4, "Чёрный")
        setBackgroundColor.add(2, 17, 5, "Белый")
        menu.add(0, -5, 2,"Инструкция")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            -5 -> {
                val d = RulerInstructionDialogFragment()
                val m = supportFragmentManager
                d.show(m, "dialog_ruler_instruction")
            }
            0 -> rulerColor = Color.RED
            1 -> rulerColor = Color.GREEN
            2 -> rulerColor = Color.BLUE
            3 -> rulerColor = Color.rgb(204, 102, 0)
            4 -> rulerColor = Color.BLACK
            5 -> rulerColor = Color.WHITE

            6 -> textColor = Color.RED
            7 -> textColor = Color.GREEN
            8 -> textColor = Color.BLUE
            9 -> textColor = Color.rgb(204, 102, 0)
            10 -> textColor = Color.BLACK
            11 -> textColor = Color.WHITE

            12 -> backgroundColor = Color.RED
            13 -> backgroundColor = Color.GREEN
            14 -> backgroundColor = Color.BLUE
            15 -> backgroundColor = Color.rgb(204, 102, 0)
            16 -> backgroundColor = Color.BLACK
            17 -> backgroundColor = Color.WHITE

            else -> {}
        }
        draw()
        return super.onOptionsItemSelected(item)
    }

    private fun draw() {
        val imageView = findViewById<ImageView>(R.id.imageView)
        val bitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val textPaint = Paint()
        val mm =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1F, resources.displayMetrics)
        paint.color = rulerColor
        paint.strokeWidth = 4F
        textPaint.textSize = 32F
        textPaint.color = textColor
        textPaint.style = Paint.Style.FILL
        canvas.drawColor(backgroundColor)
        canvas.drawRect(RectF(0F, 0F, imageView.width / 4F, imageView.height * 1F), paint)
        canvas.drawRect(RectF(imageView.width * 3F / 4F, 0F, imageView.width * 1F, imageView.height * 1F), paint)
        var st = imageView.height * 17F / 18F
        var counter = 0
        while(st >= imageView.height / 18F){
            if (counter % 10 == 0 ){
                canvas.drawLine(0F, st, imageView.width / 10F, st, textPaint)
                canvas.drawText((counter/10).toString(), imageView.width / 8F, st, textPaint)
            }
            else if (counter != 0 && counter % 5 == 0 )
                canvas.drawLine(0F, st, imageView.width / 12F, st, textPaint)
            else
                canvas.drawLine(0F, st, imageView.width / 14F, st, textPaint)
            st -= mm
            counter++
        }
        st = imageView.height / 18F
        counter = 0
        while(st <= imageView.height * 17F / 18F){
            if (counter % 10 == 0 ){
                canvas.drawLine(imageView.width * 1F, st, imageView.width * 9F / 10F, st, textPaint)
                canvas.drawText((counter/10).toString(), imageView.width * 7F / 8F, st, textPaint)
            }
            else if (counter != 0 && counter % 5 == 0 )
                canvas.drawLine(imageView.width * 1F, st, imageView.width * 11F / 12F, st, textPaint)
            else
                canvas.drawLine(imageView.width * 1F, st, imageView.width * 13F / 14F, st, textPaint)
            st += mm
            counter++
        }
        imageView.setImageBitmap(bitmap)
    }
}

class RulerInstructionDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Инструкция")
                .setMessage("\tДля того, чтобы измерить длину предмета, приложите телефон и подставте левую границу предмета к числу 0. \nПравая граница предмета будет находиться рядом с числом, указывающим на длину предмета.")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("ОК") {
                        dialog, _ ->  dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
package com.trinity.toolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttons: Array<Button> = arrayOf(findViewById(R.id.button), findViewById(R.id.button2), findViewById(R.id.button3), findViewById(R.id.button4), findViewById(R.id.button5))
        buttons[0].setOnClickListener {
            val i = Intent(this, SensorsActivity::class.java)
            startActivity(i)
        }
        buttons[1].setOnClickListener {
            val i = Intent(this, CoinActivity::class.java)
            startActivity(i)
        }
        buttons[2].setOnClickListener {
            val i = Intent(this, PunchActivity::class.java)
            startActivity(i)
        }
        buttons[3].setOnClickListener {
            val i = Intent(this, BubbleActivity::class.java)
            startActivity(i)
        }
        buttons[4].setOnClickListener {
            val i = Intent(this, RulerActivity::class.java)
            startActivity(i)
        }
    }
}

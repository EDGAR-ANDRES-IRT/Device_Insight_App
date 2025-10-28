package com.example.deviceinsight

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<MaterialButton>(R.id.Acelerómetro).setOnClickListener {
            startActivity(Intent(this, Acelerometro:: class.java)) }

        findViewById<MaterialButton>(R.id.SensorLuz).setOnClickListener {
            startActivity(Intent(this, SensorLuz::class.java)) }

        findViewById<MaterialButton>(R.id.Red_Celular).setOnClickListener {
            startActivity(Intent(this, Red_Telefonica:: class.java)) }

        findViewById<MaterialButton>(R.id.Red_Wifi).setOnClickListener {
            startActivity(Intent(this, Red_WiFi::class.java)) }
    }
}
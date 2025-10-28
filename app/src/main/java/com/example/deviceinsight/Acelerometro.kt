package com.example.deviceinsight

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Acelerometro : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var acelerometro: Sensor? = null
    private lateinit var txtX: TextView
    private lateinit var txtY: TextView
    private lateinit var txtZ: TextView
    private lateinit var txtError: TextView
    private lateinit var btnDetener : Button
    private var estado = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acelerometro)

        txtX = findViewById(R.id.TextView_ValorX)
        txtY = findViewById(R.id.TextView_ValorY)
        txtZ = findViewById(R.id.TextView_ValorZ)
        txtError = findViewById(R.id.TextView_Error)
        btnDetener = findViewById(R.id.Boton_Detener)


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (acelerometro == null){
            txtError.text= getString(R.string.Error_acelerometro)
        }

        btnDetener.setOnClickListener {
            if (estado) {
                sensorManager.unregisterListener(this)
                estado = false
                txtError.text = getString(R.string.Sensor_detenido)
                btnDetener.text = getString(R.string.Reanudar)
            }
            else {
                acelerometro?.also { sensor ->
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
                }
                estado = true
                txtError.text = ""
                btnDetener.text = getString(R.string.Detener)
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            txtX.text = "X: %.2f".format(x)
            txtY.text = "Y: %.2f".format(y)
            txtZ.text = "Z: %.2f".format(z)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume(){
        super.onResume()
        acelerometro?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
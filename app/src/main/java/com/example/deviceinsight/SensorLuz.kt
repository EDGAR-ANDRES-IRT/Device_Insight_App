package com.example.deviceinsight

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class SensorLuz : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var luz: Sensor? = null
    private lateinit var txtLx: TextView
    private lateinit var imgLx: ImageView
    private lateinit var btnDetener : Button
    private var estado = true
    private lateinit var tvDatosJSON : TextView
    private lateinit var btnGuardar: Button
    private lateinit var btnMostrar: Button
    private lateinit var btnBorrar: Button
    private val nombreArchivo = "datos_luz.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_luz)

        txtLx = findViewById(R.id.TextView_Luz)
        imgLx = findViewById(R.id.LuzImagen)
        btnDetener = findViewById(R.id.Boton_Detener)
        tvDatosJSON = findViewById(R.id.TextView_DatosJSON)
        btnGuardar = findViewById(R.id.Boton_Guardar)
        btnMostrar = findViewById(R.id.Boton_Mostrar)
        btnBorrar = findViewById(R.id.Boton_Borrar)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        luz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        btnDetener.setOnClickListener {
            if (estado) {
                sensorManager.unregisterListener(this)
                estado = false
                txtLx.text = getString(R.string.Sensor_detenido)
                btnDetener.text = getString(R.string.Reanudar)
            }
            else {
                luz?.also { sensor ->
                    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
                }
                estado = true
                btnDetener.text = getString(R.string.Detener)
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lx = event.values[0]
            txtLx.text = "Lúmenes: $lx\n${brightness(lx)}"

            when (lx) {
                in 0.0..10.0 -> imgLx.setImageResource(R.drawable.anochecer)
                in 11.0..50.0 -> imgLx.setImageResource(R.drawable.atardecer)
                in 51.0..5000.0 -> imgLx.setImageResource(R.drawable.mediodia)
                in 5001.0..25000.0 -> imgLx.setImageResource(R.drawable.aclarecer)
                else -> imgLx.setImageResource(R.drawable.aclarecer)
            }

            btnGuardar.setOnClickListener {
                guardarDatosJson(lx)
                tvDatosJSON.text = getString(R.string.Datos_guardados)
            }

            btnMostrar.setOnClickListener {
                val datos = leerDatosJsonArray()
                if(datos != null && datos.length() > 0) {
                    val texto = StringBuilder("Últimas lecturas: \n")
                    for (i in 0 until datos.length()){
                        val obj = datos.getJSONObject(i)
                        texto.append("Lumenes: ${obj.getString("Lumenes")} - Hora: ${obj.getString("Hora")}\n")
                    }
                    tvDatosJSON.text = texto.toString()
                } else {
                    tvDatosJSON.text = getString(R.string.Datos_vacios)
                }
            }

            btnBorrar.setOnClickListener {
                borrarDatosJson()
                tvDatosJSON.text = getString(R.string.Datos_borrados)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun brightness(brightness: Float): String{
        return when (brightness.toInt()){
            0 -> "Muy oscuro"
            in 1..10 -> "Oscuro"
            in 11..50 -> "Sombra"
            in 51..5000 -> "Normal"
            in 5001..25000 -> "Brillante"
            else -> "Mucha luz"
        }
    }

    override fun onResume(){
        super.onResume()
        sensorManager.registerListener(this, luz, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun guardarDatosJson(lx: Float){
        val nuevaLectura = JSONObject()
        nuevaLectura.put("Lumenes", (lx).roundToInt())
        nuevaLectura.put("Hora", hora())

        val lecturasPrevias = leerDatosJsonArray() ?: JSONArray()

        val nuevasLecturas = JSONArray()
        nuevasLecturas.put(nuevaLectura)

        for (i in 0 until lecturasPrevias.length()) {
            if (i >= 9) break
            nuevasLecturas.put(lecturasPrevias.getJSONObject(i))
        }

        openFileOutput(nombreArchivo, MODE_PRIVATE).use {
            it.write(nuevasLecturas.toString().toByteArray())
        }
    }

    private fun leerDatosJsonArray(): JSONArray? {
        return try {
            val archivo = openFileInput(nombreArchivo)
            val contenido = archivo.bufferedReader().use {it.readText()}
            JSONArray(contenido)
        } catch (e: Exception){
            null
        }
    }

    private fun borrarDatosJson(){
        val file = File(filesDir, nombreArchivo)
        if (file.exists()) file.delete()
    }

    private fun hora(): String{
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
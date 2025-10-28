@file:Suppress("DEPRECATION")

package com.example.deviceinsight

import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File

class Red_WiFi : AppCompatActivity() {
    private var tvSSID: TextView? = null
    private var tvIP: TextView? = null
    private var tvSPEED: TextView? = null
    private lateinit var tvDatosJSON: TextView
    private lateinit var btnGuardar: Button
    private lateinit var btnMostrar: Button
    private lateinit var btnBorrar: Button
    private val nombreArchivo = "wifi_data.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_red_wi_fi)

        tvSSID = findViewById(R.id.TextView_SSID)
        tvIP = findViewById(R.id.TextView_IP)
        tvSPEED = findViewById(R.id.TextView_BSSID)
        tvDatosJSON = findViewById(R.id.TextView_DatosJSON)
        btnGuardar = findViewById(R.id.Boton_Guardar)
        btnMostrar = findViewById(R.id.Boton_Mostrar)
        btnBorrar = findViewById(R.id.Boton_Borrar)

        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager

        try {
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo != null){
                val ssid = wifiInfo.ssid
                val ip = Formatter.formatIpAddress(wifiInfo.ipAddress)
                val speed = wifiInfo.linkSpeed.toString()

                tvSSID?.text = "Red: $ssid"
                tvIP?.text = "Dirección Ip: $ip"
                tvSPEED?.text = "Velocidad: $speed mbps"

                btnGuardar.setOnClickListener {
                    guardarDatosJson(ssid, ip, speed)
                    tvDatosJSON.text = getString(R.string.Datos_guardados)
                }
                btnMostrar.setOnClickListener {
                    val datos = leerDatosJson()
                    if (datos != null){
                        tvDatosJSON.text = "Última conexión:\nRed: ${datos.getString("ssid")}\nIP: ${datos.getString("ip")}\nVelocidad: ${datos.getString("speed")}"
                    } else {
                        tvDatosJSON.text = getString(R.string.Datos_vacios)
                    }
                }
                btnBorrar.setOnClickListener {
                    borrarDatosJson()
                    tvDatosJSON.text = getString(R.string.Datos_borrados)
                }
            }
        } catch (e: Exception){
            tvSSID?.text = "Desconectado"
            tvIP?.text = "N/A"
            tvSPEED?.text = "N/A"
        }
    }

    private fun guardarDatosJson(ssid: String, ip: String, speed: String){
        val json = JSONObject()
        json.put("ssid", ssid)
        json.put("ip", ip)
        json.put("speed", speed)

        openFileOutput(nombreArchivo, MODE_PRIVATE).use {
            it.write(json.toString().toByteArray())
        }
    }

    private fun leerDatosJson(): JSONObject?{
        return try {
            val archivo = openFileInput(nombreArchivo)
            val contenido = archivo.bufferedReader().use { it.readText() }
            JSONObject(contenido)
        } catch (e: Exception) {
            null
        }
    }

    private fun borrarDatosJson(){
        val file = File(filesDir, nombreArchivo)
        if (file.exists()) file.delete()
    }
}
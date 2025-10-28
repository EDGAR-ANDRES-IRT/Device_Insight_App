package com.example.deviceinsight

import android.Manifest
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Red_Telefonica : AppCompatActivity() {
    lateinit var resultadoSIM: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_red_telefonica)

        resultadoSIM = findViewById(R.id.TextView_ResultadoSIM)

        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        var resultado = ""

        try {
            resultado = resultado + "\nNombre Operador: " + telephonyManager.simOperatorName
            resultado = resultado + "\nPaís: " + telephonyManager.simCountryIso
           // resultado = resultado + "\nTipo de Red: " + tipoRed(telephonyManager.dataNetworkType)//agregar permisos necesarios
            resultado = resultado + "\nTipo de red: " + tipoRed(telephonyManager.voiceNetworkType)
        } catch (e:Exception){
            Log.i("Error", "Revisar permisos de aplicación")
        }
        resultadoSIM.text = resultado
    }

    private fun tipoRed(red: Int): String {
        var tecnologiaRadio = "Error"
        Log.i("Tipo de Red", red.toString())
         when (red) {
            0 -> {tecnologiaRadio = "Desconocida" }
            1 -> {tecnologiaRadio = "GRPS" }
            2 -> {tecnologiaRadio = "EDGE" }
            3 -> {tecnologiaRadio = "UMTS" }
            4 -> {tecnologiaRadio = "CDMA" }
            5 -> {tecnologiaRadio = "EVDO_0" }
            6 -> {tecnologiaRadio = "EVDO_A" }
             7 -> {tecnologiaRadio = "1xRTT"}
            8 -> {tecnologiaRadio = "HSDPA" }
            9 -> {tecnologiaRadio = "HSUPA" }
            10 -> {tecnologiaRadio = "HSPA" }
            11 -> {tecnologiaRadio = "IDEN" }
            12 -> {tecnologiaRadio = "EVDO_B" }
            13 -> {tecnologiaRadio = "LTE" }
            14 -> {tecnologiaRadio = "EHRPD" }
            15 -> {tecnologiaRadio = "HSPA+" }
            16 -> {tecnologiaRadio = "GSM" }
            17 -> {tecnologiaRadio = "TD_SCDMA" }
            18 -> {tecnologiaRadio = "IWLAN" }
             19 -> {tecnologiaRadio = "LTE_CA"}
            20 -> {tecnologiaRadio = "NR"}
        }
        return tecnologiaRadio
    }
}
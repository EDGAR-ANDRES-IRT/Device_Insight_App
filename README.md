## Aplicación "Device Insight"
### Elaborado por: Edgar Andres Hernandez Avila para la materia Programación de dispositivos móviles

<img width="240" height="240" alt="Icono de la aplicación Device Insight" src="https://github.com/user-attachments/assets/bab074d0-c7d1-45b8-b3c0-2945cd1b0be2" />

## Descripcion general del proyecto
Se desarrolla una aplicación en el entorno de desarrollo de Android Studio para conocer los fundamentos básicos del lenguaje Kotlin. La aplicación fue probada en un dispositivo "OnePlus-Nord 2500" con Android 15. 

<img width="auto" height="550" alt="Pantalla inicio" src="https://github.com/user-attachments/assets/538cac7b-4957-4370-b99c-9369a690c799" />


## Sensores utilizados
La aplicación <b>*"Device Insight"*</b> hace uso del sensor de luz y acelerómetro, sensores que son comunmente incluidos en los dispositivos actuales. En las actividades "Acelerometro" y "SensorLuz" se registra constantemente una lectura de cada sensor para mostrar los valores obtenidos en pantalla. 

<img width="auto" height="550" alt="Pantalla acelerómetro" src="https://github.com/user-attachments/assets/5ea0b950-602a-4c28-99c2-a4fce10db825" />

La actividad <b>*"Acelerometro"*</b> simplemente muestra los valores (X, Y, Z) sobre la orientación del dispositivo. Para variar estos valores simplemente puede hacer girar el télefono para ver los cambios. Con el botón "Detener lectura" se pausa la lectura del sensor, para volver a registrar lecturas, vuela a presionar el bóton.

<img width="auto" height="550" alt="Pantalla sensor de luz" src="https://github.com/user-attachments/assets/0826df81-a2c4-4966-9471-6f9c6107e420" />

El sensor de luz se muestra dentro de la actividad <b>*"Sensor Luz"*</b>, iniciada la actividad se muestra el valor en lúmenes de la luz percibida en el entorno, la imagen mostrada cambiará según el valor obtenido. Es posible detener y reanudar el sensor con el botón "Detener lectura". Los botones restantes se utilizan para guardar, mostrar y borrar las útlimas 10 lecturas del sensor en el archivo <b>"datos_luz.json"</b>.

[Pantallas red telefónica](./Telefonia_page.jpg)
[Pantalla red inalámbrica](./Wifi_page.jpg)

<img width="auto" height="550" alt="Pantalla WiFi" src="https://github.com/user-attachments/assets/e29fd516-23f5-47bd-a8b8-0c60be0b32f2" />
<img width="auto" height="550" alt="Pantalla Red Celular" src="https://github.com/user-attachments/assets/510c708f-2380-4529-b309-0fb6ac4853f0" />

Las actividades <b>*"Red telefónica"*</b> y <b>*"Red inalámbrica"*</b> muestran datos básicos sobre la conectividad del dispositvo como: nombre del operador móvil, tipo de red, nombre de red WiFi (SSID), dirección IP. 
En la pantalla de <b>*"Red inalámbrica*</b> es posible guardar los datos obtenidos para mostrarlos en el futuro. De igual manera que con el sensor de luz, es posible guardar, mostrar y borrar los datos de la última conexión en el archivo <b>"wifi_data.json"</b>

## Permisos requeridos y su propósito 
Es necesario conceder permisos de teléfono y ubicación a la aplicación, especialmente para acceder a información sobre la red telefónica e inalámbrica. De caso contrario, no se obtendrán correctamente los datos. 
De igual manera es importante, tener encendido el servicio de GPS durante el uso de la aplicación. 

<b>Los permisos deberán configurarse manualmente desde la configuración de la aplicación (gestor de aplicaciones).</b>

## Librerias utilizadas

* [Sensor manager](https://developer.android.com/reference/android/hardware/SensorManager)
* [TelephonyManager](https://developer.android.com/reference/android/telephony/TelephonyManager)
* [WifiManager](https://developer.android.com/reference/android/net/wifi/WifiManager)
* [WifiInfo](https://developer.android.com/reference/android/net/wifi/WifiInfo)

package com.example.plantlife

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.light_sensor_fragment.*

class LightSensorFragment : Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var resume = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.light_sensor_fragment, container, false)
        val startButton = view.findViewById<View>(R.id.lightSensorButton)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        startButton.setOnClickListener {
            if (this.resume) {
                pauseReading()
            } else {
                resumeReading()
            }
        }
        return view
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && resume) {
            if (event.sensor.type === Sensor.TYPE_LIGHT) {
                lightSensorText.text = event.values[0].toString()
                when (event.values[0]) {
                    in 32000.1..Float.MAX_VALUE.toDouble() -> directSunlight()
                    in 10000.0..32000.0 -> indirectSunlight()
                    in 1000.0..9999.9 -> halfShadow()
                    in 100.0..999.9 -> shadow()
                    else -> tooDark()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    fun resumeReading() {
        this.resume = true
        lightSensorButton.text = "Stop "
        lightSensorButton.setBackgroundColor(Color.RED)
    }

    fun pauseReading() {
        this.resume = false
        lightSensorButton.text = "Start"
        lightSensorButton.setBackgroundColor(Color.parseColor("#00BF5F"))
    }

    private fun directSunlight() {
        lightSensorValue.text = "Direct Sunlight"
        weatherIcon.setImageResource(R.drawable.ic_directsunlight)
    }

    private fun indirectSunlight() {
        lightSensorValue.text = "Indirect Sunlight"
        weatherIcon.setImageResource(R.drawable.ic_halfshadow)
    }

    private fun halfShadow() {
        lightSensorValue.text = "Half shadow"
        weatherIcon.setImageResource(R.drawable.ic_halfshadow)
    }

    private fun shadow() {
        lightSensorValue.text = "Shadow"
        weatherIcon.setImageResource(R.drawable.ic_cloudy)
    }

    private fun tooDark() {
        lightSensorValue.text = "Too dark"
        weatherIcon.setImageResource(R.drawable.ic_toodark)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
package br.com.guilhermesantana.canvasexemples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_solar_system.*

class SolarSystemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_system)
        solarSystem.startAnimation()
        btnLess.setOnClickListener {
            solarSystem.removePlanet()
        }
        btnPlus.setOnClickListener {
            solarSystem.addPlanet()
        }
        btnMoon.setOnClickListener {
            solarSystem.createMoons()
        }
    }
}

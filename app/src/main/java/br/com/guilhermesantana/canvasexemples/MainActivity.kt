package br.com.guilhermesantana.canvasexemples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnDrawing.setOnClickListener {
            Intent(this, DrawingActivity::class.java).apply {
                startActivity(this)
            }
        }
        btnMesh.setOnClickListener {
            Intent(this, BitmapMeshActivity::class.java).apply {
                startActivity(this)
            }
        }

        btnSolarSystem.setOnClickListener {
            Intent(this, SolarSystemActivity::class.java).apply {
                startActivity(this)
            }
        }

        btnTextPath.setOnClickListener {
            Intent(this, TextOnPathActivity::class.java).apply {
                startActivity(this)
            }
        }

        btnAutorama.setOnClickListener {
            Intent(this, AutoramaActivity::class.java).apply {
                startActivity(this)
            }
        }

        btnProgress.setOnClickListener {
            Intent(this, ProgressActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}

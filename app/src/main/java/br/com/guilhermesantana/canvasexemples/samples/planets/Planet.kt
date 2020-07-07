package br.com.guilhermesantana.canvasexemples.samples.planets

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import br.com.guilhermesantana.canvasexemples.dp
import java.util.*

class Planet : SolarSystemItem {

    var x = 0f
    var y = 0f
    var radio = 0f
    var progress = 0f
    var progressIncremention = 1/100f
    var orbit : Orbit ? = null
    var planetPath = Path()
    val planetPaint = Paint().apply {
        color = getRandomColor()
        style = Paint.Style.FILL
    }

    fun getRandomColor(): Int{
        val rnd = Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    init {
        calcPath()
    }

    fun calcPath(){
        planetPath = Path().apply {
            addCircle(x, y, radio, Path.Direction.CW)
        }
        orbit?.calculatePath()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(planetPath, planetPaint)
        orbit?.draw(canvas)
    }

    override fun invalidate() {
        calcPath()
        orbit?.apply {
            radio = this@Planet.radio * 1.7f
            x = this@Planet.x
            y = this@Planet.y
            orbitStrokeWidth = 0.2f.dp
            invalidate()
        }
    }

    fun createOrbit(){
        orbit = Orbit(1)
        invalidate()
    }

    override fun incrementProgress() {
        orbit?.incrementProgress()
        progress += progressIncremention
        if (progress >= 1 )
            progress = 0f
    }

}
package br.com.guilhermesantana.canvasexemples.samples.planets

import android.graphics.*

class Orbit(val level:Int = 0) : SolarSystemItem {

    var planets = listOf<Planet>(
        Planet().apply { radio = 20f/(level+1) }
    )
    var x: Float = 0f
    var y: Float = 0f
    var radio: Float = 0f
    var orbitStrokeWidth:Float = 0f

    var orbitPath = Path()
    var orbitPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = orbitStrokeWidth
    }

    override fun incrementProgress() {
        planets.forEach {
            it.incrementProgress()
        }
        invalidate()
    }


    init {
        calculatePath()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(orbitPath, orbitPaint)
        planets.forEach {
            it.draw(canvas)
        }
    }

    fun calculatePath(){
        orbitPath = Path().apply {
            addCircle(x, y,radio, Path.Direction.CW)
        }

        planets.forEach {
            //position of the planet
            var pos = FloatArray(2)

            PathMeasure().apply {
                setPath(orbitPath,false)
                //Retorna a posição e a tangente de um ponto do path
                getPosTan(it.progress * length, pos, null)
                it.progressIncremention = 1 / length
            }

            it.x = pos[0]
            it.y = pos[1]
            it.invalidate()
        }
    }

    fun createMoons(){
        planets.forEach {
            it.createOrbit()
        }
    }

    override fun invalidate() {
        calculatePath()
        planets.forEach {
            it.invalidate()
        }
    }




}
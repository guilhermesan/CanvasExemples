package br.com.guilhermesantana.canvasexemples.samples.planets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import br.com.guilhermesantana.canvasexemples.dp
import kotlinx.coroutines.*

class SolarSystemWidget : View {

    var sunRadio:Float = 0f
    var orbits = mutableListOf<Orbit>()
    var orbitsCount = 5
    set(value) {
        field = value
        calculateOrbit()
    }

    var isAnimating = false

    var sunPath = Path()
    val sunPaint = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.FILL
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        sunRadio = 30.dp
    }

    fun calculatePath(){
        sunPath = Path().apply {
            addCircle(width/2f, height/2f, sunRadio, Path.Direction.CW)
        }
    }

    fun calculateOrbit(){
        orbits.clear()
        for (i in 1 .. orbitsCount) {
            orbits.add(Orbit().apply {
                radio = sunRadio * (i*1.5f)
                x = width / 2f
                y = height / 2f
                orbitStrokeWidth = 0.5f.dp
                invalidate()
            })
        }
    }

    fun createMoons(){
        orbits.forEach {
            it.createMoons()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculatePath()
        calculateOrbit()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawPath(sunPath, sunPaint)
            orbits.forEach {
                it.draw(this)
            }
        }
    }

    fun startAnimation(){
        isAnimating = true
        CoroutineScope(Dispatchers.IO).launch {
            while (isAnimating){
                delay(2)
                withContext(Dispatchers.Main){
                    orbits.forEach { it.incrementProgress() }
                    invalidate()
                }
            }
        }
    }

    fun stopAnimation(){
        isAnimating = false
    }

    fun addPlanet(){
        orbits.add(Orbit().apply {
            radio = sunRadio * ((orbits.size+1)*1.5f)
            x = width / 2f
            y = height / 2f
            orbitStrokeWidth = 0.5f.dp
            invalidate()
        })
    }

    fun removePlanet(){
        orbits.removeAt(orbits.size-1)
    }


}
package br.com.guilhermesantana.canvasexemples.samples.planets

import android.graphics.Canvas

interface SolarSystemItem {

    fun draw(canvas: Canvas)
    fun invalidate()
    fun incrementProgress()

}
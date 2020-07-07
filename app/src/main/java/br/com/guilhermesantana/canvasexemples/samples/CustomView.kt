package br.com.guilhermesantana.canvasexemples.samples

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import br.com.guilhermesantana.canvasexemples.dp

class CircleView constructor(context: Context,
                              attrs: AttributeSet? = null,
                              defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr){

    private val paint = Paint().apply {
        color = Color.RED
        strokeWidth = 1.dp
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawCircle(width/2f,height/2f, 100.dp, paint)
        }
    }
}


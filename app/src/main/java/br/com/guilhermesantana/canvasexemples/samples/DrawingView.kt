package br.com.guilhermesantana.canvasexemples.samples

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import br.com.guilhermesantana.canvasexemples.dp

class DrawingView : View{

    var path = Path()
    var paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 3.dp
        isAntiAlias = true
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        setOnTouchListener { view, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(event.x,event.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(event.x,event.y)
                }
            }
            invalidate()
            true
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
           drawPath(path, paint)
        }
    }
}
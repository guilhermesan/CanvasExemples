package br.com.guilhermesantana.canvasexemples

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce


class TextAndPathWidget : View {

    var linePath: Path? = null;
    var midPoint: PointF? = null
    set(value) {
        field = value
        invalidate()
    }

    var text = "Awesome Canvas"

    val textPaint = TextPaint().apply {
        color = Color.BLACK
        textSize = 100.dp
    }

    val pathPaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 10.dp
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    override fun invalidate() {
        calcLinePath()
        super.invalidate()
    }

    var textBounds = Rect()

    private val floatPropertyAnimX = object : FloatPropertyCompat<TextAndPathWidget>("mid_x") {
        override fun setValue(dropper: TextAndPathWidget?, value: Float) {
            Log.i("pointX", value.toString())
            dropper?.midPoint?.x = value
            dropper?.invalidate()

        }

        override fun getValue(dropper: TextAndPathWidget?): Float {
            return dropper?.midPoint?.x?:0f
        }
    }

    private val floatPropertyAnimY = object : FloatPropertyCompat<TextAndPathWidget>("mid_y") {
        override fun setValue(dropper: TextAndPathWidget?, value: Float) {
            Log.i("pointY", value.toString())
            dropper?.midPoint?.y = value
            dropper?.invalidate()
        }

        override fun getValue(dropper: TextAndPathWidget?): Float {
            return dropper?.midPoint?.y?:0f
        }
    }

    private fun animateToPoint(point: PointF) {
        SpringAnimation(this, floatPropertyAnimX, point.x).apply {
            spring.stiffness = SpringForce.STIFFNESS_HIGH
            spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            start()
        }

        SpringAnimation(this, floatPropertyAnimY, point.y).apply {
            spring.stiffness = SpringForce.STIFFNESS_HIGH
            spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            start()
        }
    }


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        setOnTouchListener(object : OnTouchListener{
            override fun onTouch(p0: View, p1: MotionEvent): Boolean {
                when(p1.action){
                    MotionEvent.ACTION_UP -> {
                        animateToPoint(PointF(width/2f, height/2f))
                    }
                    else -> {
                        midPoint = PointF(p1.x, p1.y)
                        Log.i("pointXY", p1.x.toString()+"-"+p1.y)
                    }

                }
                return true
            }

        })
    }

    fun calcTextBounds(){
        textBounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, textBounds)
    }

    fun calcLinePath(){
        if (midPoint == null)
            midPoint = PointF(width/2f, height/2f)
        calcTextBounds()
        linePath = Path().apply {
            moveTo(0f, height/2f)
            quadTo(midPoint?.x?:0f, midPoint?.y?:0f,width.toFloat(), height/2f)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calcLinePath()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        linePath?.let { path ->
            canvas?.drawPath(path, pathPaint)
            //Desenha o texto sobre um path
            canvas?.drawTextOnPath(text, path, (width/2f)-(textBounds.width()/2),-50.dp, textPaint)
        }
    }
}
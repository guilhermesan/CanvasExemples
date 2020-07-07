package br.com.guilhermesantana.canvasexemples.samples

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import br.com.guilhermesantana.canvasexemples.R
import br.com.guilhermesantana.canvasexemples.copyWithTint
import br.com.guilhermesantana.canvasexemples.dp
import kotlin.math.abs
import kotlin.math.atan2


class AutoramaView : View {

    var path = Path()
    var progress = 0f
    var paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 50.dp
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    var paintDashed = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 10.dp
        pathEffect = DashPathEffect(floatArrayOf(20.dp, 20.dp), 0f)//Aplicar efeito dash
        isAntiAlias = true
    }


    var pos = FloatArray(2)
    var tan = FloatArray(2)

    val car = ContextCompat.getDrawable(context, R.drawable.car)?.copyWithTint(Color.RED)
    var mesure : PathMeasure? = null
    val carSize = 80.dp.toInt()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){

        setOnTouchListener { view, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    path = Path()
                    path.moveTo(event.x,event.y)
                }
                MotionEvent.ACTION_UP -> {
                    path.close()
                    car?.setBounds(carSize/-2, carSize/-2, carSize/2, carSize/2);
                    start()
                }
                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(event.x, event.y)
                }
            }
            invalidate()
            true
        }

    }

    //Desenha o carro na pista
    fun putCarOnPath(canvas: Canvas){
        canvas.translate(pos[0], pos[1])
        val angle = atan2(tan[1].toDouble(), tan[0].toDouble())//angulo em Radianos
        canvas.rotate(Math.toDegrees(angle).toFloat())
        car?.draw(canvas)
    }

    //Calcula a tangente durante a animação
    fun start(){
        ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                progress = it.animatedValue as Float
                mesure = PathMeasure().apply {
                    setPath(path, true)
                    //pos é a posição x, y do carrinho
                    //tan é o valor na reta tangente
                    // que será utilizada pra calcular o angulo do carrinho
                    getPosTan(progress * length, pos, tan)
                }
                repeatCount = -1
                repeatMode = ValueAnimator.RESTART
                invalidate()
            }
            duration = 5000
            start()
        }
    }




    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawPath(path, paint)
            drawPath(path, paintDashed)
            putCarOnPath(this)
        }
    }

}
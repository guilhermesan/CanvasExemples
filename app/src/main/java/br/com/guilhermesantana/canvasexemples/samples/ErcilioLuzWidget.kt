package br.com.guilhermesantana.canvasexemples.samples

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

import android.view.View
import br.com.guilhermesantana.canvasexemples.R


class ErcilioLuzWidget : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
    }

    companion object {
        const val MESH_WIDTH = 2
        const val MESH_HEIGHT = 2
    }
    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        setOnTouchListener { view, motionEvent ->
            centerPoint = PointF(motionEvent.x, motionEvent.y)
            calculateVerts()
            invalidate()
            true
        }
    }

    var bitmap:Bitmap? = null

    var points: List<PointF>? = null

    var centerPoint: PointF? = null

    fun calculateVerts(){
        var points = mutableListOf<PointF>()
        val hSlice = width / MESH_WIDTH
        val vSlice = height / MESH_HEIGHT
        for (col in 0 .. MESH_WIDTH) {
            for (row in 0..MESH_HEIGHT) {
                centerPoint?.let {
                    if (col == centerPoint(MESH_WIDTH) && row == centerPoint(MESH_HEIGHT))
                        points.add(it)
                    else
                        points.add(PointF(row*hSlice.toFloat(), col*vSlice.toFloat()))
                }?:points.add(PointF(row*hSlice.toFloat(), col*vSlice.toFloat()))
            }
        }
        this.points = points
    }

    fun centerPoint(value:Int) = (value + (value%2))/2



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bridge), width, height,false)
        calculateVerts()
        invalidate()
    }

    private fun drawLines(canvas: Canvas, coordinates:List<PointF>) {

        coordinates.forEachIndexed { index, pair ->
            // Draw horizontal line with next column
            if (((index + 1) % (MESH_WIDTH + 1)) != 0) {
                val nextCoordinate = coordinates[index + 1]
                drawLine(canvas, pair, nextCoordinate)
            }

            // Draw horizontal line with next row
            if (((index < (MESH_WIDTH + 1) * MESH_HEIGHT))) {
                val nextCoordinate = coordinates[index + MESH_WIDTH + 1]
                drawLine(canvas, pair, nextCoordinate)
            }
        }
    }

    private fun drawLine(canvas: Canvas, pair: PointF, nextCoordinate: PointF) {
        canvas.drawLine(
            pair.x, pair.y,
            nextCoordinate.x, nextCoordinate.y,
            paint
        )
    }




    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmap?.let {
            points?.let {verts ->
                val points = verts.flatMap { listOf(it.x, it.y) }.toFloatArray()
                canvas.drawBitmapMesh(it, MESH_WIDTH,MESH_HEIGHT, points, 0 ,null, 0 ,null)
                canvas.drawPoints(points, paint)
                drawLines(canvas, verts)
            }
        }
    }
}
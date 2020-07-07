package br.com.guilhermesantana.canvasexemples.samples

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import br.com.guilhermesantana.canvasexemples.dp

class SquaredProgressView: View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }

    var mPaint: Paint? = null
    var mPath: Path? = null
    var mOriginalPath: Path? = null
    var paintBackStroke: Paint? = null
    var mStrokeColor: Int = Color.RED
    var mStrokeWidth: Float = 5.dp
    var mProgress = 0.0f
    var mLength = 0f
    var max = 0f

    private var onProgressChangeListener: OnProgressChangeListener? = null



    private fun init() {
        val dm = resources.displayMetrics
        val strokeBackWidth = dm.density.toInt() * 1
        mPaint = Paint()
        mPaint!!.color = mStrokeColor
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = mStrokeWidth
        mPaint!!.strokeCap = Paint.Cap.ROUND
        mPaint!!.isAntiAlias = true
        mPaint!!.isDither = true
        paintBackStroke = Paint()
        paintBackStroke!!.isAntiAlias = true
        paintBackStroke!!.style = Paint.Style.STROKE
        paintBackStroke!!.strokeWidth = strokeBackWidth.toFloat()
        paintBackStroke!!.color = Color.GRAY
        setPath(Path())
        // setPath2(new Path());
    }

    fun setPath(p: Path) {
        mOriginalPath = p
        val measure = PathMeasure(mOriginalPath, false)
        mLength = measure.length
        mPath = Path()
        measure.getSegment(0f, mProgress * mLength, mPath, true)
    }

    fun setPath() {
        val p = RoundedRect(
            10f,
            10f,
            width - 10.toFloat(),
            height - 10.toFloat(),
            width * 0.2f,
            height * 0.2f
        )
        setPath(p)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        canvas.drawPath(mOriginalPath!!, paintBackStroke!!)
        canvas.drawPath(mPath!!, mPaint!!)
        canvas.restore()
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        setPath()
    }

    fun animateToProgress(progress: Float, time: Int) {
        val value = progress * 100f / max / 100
        val anim = ObjectAnimator.ofFloat(this, "currentProgress", mProgress, value)
        anim.duration = time.toLong()
        anim.interpolator = LinearInterpolator()
        anim.addUpdateListener { animation -> setCurrentProgress(animation.getAnimatedValue("currentProgress") as Float) }
        anim.start()
    }

    fun setCurrentProgress(value: Float) {
        mProgress = value
        setPath()
        if (onProgressChangeListener != null) onProgressChangeListener!!.onProgressChange(value * 100)
        invalidate()
    }

    interface OnProgressChangeListener {
        fun onProgressChange(progress: Float)
    }

    fun setOnProgressChangeListener(onProgressChangeListener: OnProgressChangeListener?) {
        this.onProgressChangeListener = onProgressChangeListener
    }

    companion object {
        fun RoundedRect(
            left: Float,
            top: Float,
            right: Float,
            bottom: Float,
            rx: Float,
            ry: Float
        ): Path {
            var rx = rx
            var ry = ry
            val path = Path()
            if (rx < 0) rx = 0f
            if (ry < 0) ry = 0f
            val width = right - left
            val height = bottom - top
            if (rx > width / 2) rx = width / 2
            if (ry > height / 2) ry = height / 2
            val widthMinusCorners = width - 2 * rx
            val middle = left + right / 2
            path.moveTo(middle, top)
            path.lineTo(right - rx, top)
            path.rQuadTo(rx, 0f, rx, ry)
            path.lineTo(right, bottom - ry)
            path.rQuadTo(0f, ry, -rx, ry)
            path.rLineTo(-widthMinusCorners, 0f)
            path.rQuadTo(-rx, 0f, -rx, -ry)
            path.lineTo(left, top + ry)
            path.rQuadTo(0f, -ry, rx, -ry)
            path.lineTo(middle, top)
            return path
        }
    }


}
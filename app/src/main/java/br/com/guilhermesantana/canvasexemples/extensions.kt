package br.com.guilhermesantana.canvasexemples

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.core.graphics.drawable.DrawableCompat

val Int.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)


val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)


val Float.sp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this,Resources.getSystem().displayMetrics)


fun Drawable.copyWithTint(color:Int): Drawable {
    val wrappedDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTint(wrappedDrawable, color)
    return  wrappedDrawable
}
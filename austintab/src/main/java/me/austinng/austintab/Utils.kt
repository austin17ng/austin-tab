package me.austinng.austintab

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

internal object Utils {
    fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels
    fun convertDpToPx(dp: Int, context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}
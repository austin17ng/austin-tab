package me.austinng.austintab

import android.animation.TypeEvaluator

internal class IndicatorEvaluator : TypeEvaluator<IndicatorBounds> {
    override fun evaluate(
        fraction: Float,
        startIndicatorBounds: IndicatorBounds,
        endIndicatorBounds: IndicatorBounds
    ): IndicatorBounds {
        return IndicatorBounds(
            left = startIndicatorBounds.left + ((endIndicatorBounds.left - startIndicatorBounds.left) * fraction).toInt(),
            right = startIndicatorBounds.right + ((endIndicatorBounds.right - startIndicatorBounds.right) * fraction).toInt()
        )
    }
}
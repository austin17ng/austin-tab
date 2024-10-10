package me.austinng.austintab

internal class IndicatorBounds(
    var left: Int,
    var right: Int,
) {
    fun copy(indicatorBounds: IndicatorBounds) {
        this.left = indicatorBounds.left
        this.right = indicatorBounds.right
    }

    fun update(left: Int, right: Int) {
        this.left = left
        this.right = right
    }
}
package me.austinng.austintab

internal enum class IndicatorStyle {
    TAB, SEGMENTED_CONTROL;

    companion object {
        fun fromInt(value: Int) = when (value) {
            0 -> TAB
            1 -> SEGMENTED_CONTROL
            else -> throw IllegalArgumentException()
        }
    }
}
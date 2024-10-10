package me.austinng.austintab

internal enum class BadgePosition {
    TOP, CENTER, BOTTOM;
    companion object {
        fun fromInt(value: Int) = when (value) {
            0 -> TOP
            1 -> CENTER
            2 -> BOTTOM
            else -> throw IllegalArgumentException("$value is not supported")
        }
    }

}
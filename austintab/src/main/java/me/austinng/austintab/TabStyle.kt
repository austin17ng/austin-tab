package me.austinng.austintab

internal enum class TabStyle {
    ADAPTIVE, EQUAL;
    companion object {
        fun fromInt(value: Int) = when (value) {
            0 -> ADAPTIVE
            1 -> EQUAL
            else -> throw IllegalArgumentException()
        }
    }
}
package me.austinng.austintab

import androidx.annotation.DrawableRes

data class TabData(
    val name: String,
    @DrawableRes val icon: Int? = null,
    val badge: String? = null
)

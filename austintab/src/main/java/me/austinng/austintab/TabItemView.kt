package me.austinng.austintab

import android.content.Context
import android.view.Gravity.CENTER
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

internal class TabItemView(context: Context?) : LinearLayout(context) {
    var imgIcon: ImageView
    var tvNameFake: TextView
    var tvName: TextView
    var tvBadge: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_austin_tab, this, true)
        this.orientation = HORIZONTAL
        gravity = CENTER
        imgIcon = findViewById(R.id.imgAustinTabIcon)
        tvNameFake = findViewById(R.id.tvAustinTabNameFake)
        tvName = findViewById(R.id.tvAustinTabName)
        tvBadge = findViewById(R.id.tvAustinTabBadge)
    }
}
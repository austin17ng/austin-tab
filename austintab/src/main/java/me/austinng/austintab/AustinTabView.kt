package me.austinng.austintab

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.viewpager2.widget.ViewPager2

class AustinTabView(context: Context, attrs: AttributeSet?) : HorizontalScrollView(context, attrs) {
    private lateinit var container: AustinTabContainerView

    private var offset: Int = 0
    private var enableAutoScroll = true
    private var containerPadding: Int = 0
    private var containerBackground: Drawable? = null

    fun setData(data: List<TabData>) {
        container.setData(data)
    }

    fun setTabSelectedListener(listener: (index: Int) -> Unit) {
        container.tabSelectedListener = listener

    }

    fun setTabReselectedListener(listener: (index: Int) -> Unit) {
        container.tabReselectedListener = listener
    }

    fun setTabUnSelectedListener(listener: (index: Int) -> Unit) {
        container.tabUnSelectedListener = listener
    }

    fun attachWithViewPager2(viewPager2: ViewPager2) {
        container.attachWithPager2(viewPager2)
    }


    init {
        inflate(getContext(), R.layout.layout_austin_tab, this)
        findViews()
        initAttrs(attrs)
        applyAttrs()
        this.isHorizontalScrollBarEnabled = false
    }

    private fun findViews() {
        container = findViewById(R.id.viewAustinTabContainer)
        container.onTabItemViewClicked = {
            onTabItemViewClicked(it)
        }
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AustinTabView)

        // container
        containerPadding =
            typedArray.getDimensionPixelSize(R.styleable.AustinTabView_tab_container_padding, 0)
        containerBackground = typedArray.getDrawable(R.styleable.AustinTabView_tab_container_background)
        offset = typedArray.getDimensionPixelSize(R.styleable.AustinTabView_offset, 0)
        enableAutoScroll = typedArray.getBoolean(R.styleable.AustinTabView_enable_auto_scroll, true)

        // tab
        container.tabStyle = TabStyle.fromInt(typedArray.getInt(R.styleable.AustinTabView_tab_style, 0))
        container.indicatorStyle =
            IndicatorStyle.fromInt(typedArray.getInt(R.styleable.AustinTabView_indicator_style, 0))
        container.tabPadding = typedArray.getDimensionPixelSize(R.styleable.AustinTabView_tab_padding, 0)
        container.indicator = typedArray.getDrawable(R.styleable.AustinTabView_indicator_src)
            ?: throw Exception("indicator_src not found")
        container.enableAnimation = typedArray.getBoolean(R.styleable.AustinTabView_enable_animation, true)
        container.textColorActive = typedArray.getColor(R.styleable.AustinTabView_tab_text_color_active, 0)
        container.textColorInactive = typedArray.getColor(R.styleable.AustinTabView_tab_text_color_inactive, 0)
        container.indicatorHeight = typedArray.getDimensionPixelSize(R.styleable.AustinTabView_indicator_height, 0)
        if (typedArray.hasValue(R.styleable.AustinTabView_tab_text_font_active)) {
            container.textFontActive = typedArray.getResourceId(R.styleable.AustinTabView_tab_text_font_active, 0)
        }
        if (typedArray.hasValue(R.styleable.AustinTabView_tab_text_font_inactive)) {
            container.textFontInactive = typedArray.getResourceId(R.styleable.AustinTabView_tab_text_font_inactive, 0)
        }
        if (typedArray.hasValue(R.styleable.AustinTabView_tab_text_size)) {
            container.textSize = typedArray.getDimension(R.styleable.AustinTabView_tab_text_size, 0F)
        }

        typedArray.recycle()
    }

    private fun applyAttrs() {
        container.background = containerBackground
        container.setPadding(containerPadding)
        setOffset(offset)
    }

    private fun setOffset(offset: Int) {
        val params = container.layoutParams as LinearLayout.LayoutParams
        params.setMargins(offset, 0, offset, 0)
        container.layoutParams = params
        container.minimumWidth = Utils.getScreenWidth() - 2 * offset
    }

    private fun onTabItemViewClicked(tabItemView: TabItemView) {
        if (!enableAutoScroll) return
        val tabX = tabItemView.x + offset
        val tabWith = tabItemView.width
        val tabCenter = tabX + tabWith / 2
        val screenWidth = Utils.getScreenWidth()
        this.smoothScrollTo((tabCenter - screenWidth / 2).toInt(), 0)
    }

}
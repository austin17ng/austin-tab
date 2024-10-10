package me.austinng.austintab

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
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

    fun getIndex(): Int {
        return container.currentIndex
    }

    fun setIndex(index: Int) {
        container.post {
            container.onTabClicked(index)
        }
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

        container.tabStyle = TabStyle.fromInt(typedArray.getInt(R.styleable.AustinTabView_tab_style, 0))
        container.indicatorStyle = IndicatorStyle.fromInt(typedArray.getInt(R.styleable.AustinTabView_indicator_style, 0))

        // container
        containerPadding = typedArray.getDimensionPixelSize(R.styleable.AustinTabView_tab_container_padding, 0)
        containerBackground =
            if (typedArray.hasValue(R.styleable.AustinTabView_tab_container_background)) {
                typedArray.getDrawable(R.styleable.AustinTabView_tab_container_background)
            } else {
                when (container.indicatorStyle) {
                    IndicatorStyle.TAB -> ContextCompat.getDrawable(context, R.drawable.shape_austin_tab_default_tab_bg)

                    IndicatorStyle.SEGMENTED_CONTROL -> ContextCompat.getDrawable(context, R.drawable.shape_austin_tab_default_segmented_bg)
                }
            }
        offset = typedArray.getDimensionPixelSize(R.styleable.AustinTabView_offset, Utils.convertDpToPx(16, context))
        enableAutoScroll = typedArray.getBoolean(R.styleable.AustinTabView_enable_auto_scroll, true)

        // tab
        container.tabVerticalPadding = typedArray.getDimensionPixelSize(R.styleable.AustinTabView_tab_vertical_padding,  Utils.convertDpToPx(6, context))
        container.tabHorizontalPadding = typedArray.getDimensionPixelSize(R.styleable.AustinTabView_tab_horizontal_padding,  Utils.convertDpToPx(16, context))
        container.indicatorDrawable = if (typedArray.hasValue(R.styleable.AustinTabView_indicator_src)) {
                typedArray.getDrawable(R.styleable.AustinTabView_indicator_src)!!
            } else {
                when (container.indicatorStyle) {
                    IndicatorStyle.TAB -> ContextCompat.getDrawable(context, R.drawable.shape_austin_tab_default_tab_indicator)!!

                    IndicatorStyle.SEGMENTED_CONTROL -> ContextCompat.getDrawable(context, R.drawable.shape_austin_tab_default_segmented_indicator)!!
                }
            }
        container.enableAnimation = typedArray.getBoolean(R.styleable.AustinTabView_enable_animation, false)
        container.textColorActive = if (typedArray.hasValue(R.styleable.AustinTabView_tab_text_color_active)) {
                typedArray.getColor(R.styleable.AustinTabView_tab_text_color_active, 0)
            } else {
                when (container.indicatorStyle) {
                    IndicatorStyle.TAB -> ContextCompat.getColor(context, R.color.austin_tab_black_01)

                    IndicatorStyle.SEGMENTED_CONTROL -> ContextCompat.getColor(context, R.color.austin_tab_white)
                }
            }
        container.textColorInactive =
            if (typedArray.hasValue(R.styleable.AustinTabView_tab_text_color_inactive)) {
                typedArray.getColor(R.styleable.AustinTabView_tab_text_color_inactive, 0)
            } else {
                when (container.indicatorStyle) {
                    IndicatorStyle.TAB -> ContextCompat.getColor(context, R.color.austin_tab_black_01)

                    IndicatorStyle.SEGMENTED_CONTROL -> ContextCompat.getColor(context, R.color.austin_tab_black_01)
                }
            }
        container.indicatorHeight = typedArray.getDimensionPixelSize(
            R.styleable.AustinTabView_indicator_height,
            Utils.convertDpToPx(2, context)
        )
        if (typedArray.hasValue(R.styleable.AustinTabView_tab_text_font_active)) {
            container.textFontActive =
                typedArray.getResourceId(R.styleable.AustinTabView_tab_text_font_active, 0)
        }
        if (typedArray.hasValue(R.styleable.AustinTabView_tab_text_font_inactive)) {
            container.textFontInactive =
                typedArray.getResourceId(R.styleable.AustinTabView_tab_text_font_inactive, 0)
        }
        if (typedArray.hasValue(R.styleable.AustinTabView_tab_text_size)) {
            container.textSize =
                typedArray.getDimension(R.styleable.AustinTabView_tab_text_size, 0F)
        }
        if (typedArray.hasValue(R.styleable.AustinTabView_tab_badge_text_size)) {
            container.badgeTextSize =
                typedArray.getDimension(R.styleable.AustinTabView_tab_badge_text_size, 0F)
        }
        container.badgeDrawable =
            if (typedArray.hasValue(R.styleable.AustinTabView_tab_badge_source)) {
                typedArray.getDrawable(R.styleable.AustinTabView_tab_badge_source)
            } else {
                ContextCompat.getDrawable(context, R.drawable.shape_austin_tab_default_badge)!!
            }
        if (typedArray.hasValue(R.styleable.AustinTabView_tab_badge_font)) {
            container.badgeFont =
                typedArray.getResourceId(R.styleable.AustinTabView_tab_badge_font, 0)
        }
        container.badgeColor = if (typedArray.hasValue(R.styleable.AustinTabView_tab_badge_color)) {
            typedArray.getColor(R.styleable.AustinTabView_tab_badge_color, 0)
        } else {
            ContextCompat.getColor(context, R.color.austin_tab_white)
        }
        if (typedArray.hasValue(R.styleable.AustinTabView_tab_badge_position)) {
            container.badgePosition = BadgePosition.fromInt(typedArray.getInt(R.styleable.AustinTabView_tab_badge_position, 0))
        }
        container.iconActiveColor = if (typedArray.hasValue(R.styleable.AustinTabView_tab_icon_active_color)) {
                typedArray.getColor(R.styleable.AustinTabView_tab_icon_active_color, 0)
            } else {
                when (container.indicatorStyle) {
                    IndicatorStyle.TAB ->  ContextCompat.getColor(context, R.color.austin_tab_black_01)
                    IndicatorStyle.SEGMENTED_CONTROL ->  ContextCompat.getColor(context, R.color.austin_tab_white)
                }

            }
        container.iconInactiveColor = if (typedArray.hasValue(R.styleable.AustinTabView_tab_icon_inactive_color)) {
                typedArray.getColor(R.styleable.AustinTabView_tab_icon_inactive_color, 0)
            } else {
                when (container.indicatorStyle) {
                    IndicatorStyle.TAB ->  ContextCompat.getColor(context, R.color.austin_tab_black_01)
                    IndicatorStyle.SEGMENTED_CONTROL ->  ContextCompat.getColor(context, R.color.austin_tab_black_01)
                }
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
package me.austinng.austintab

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.Space
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.viewpager2.widget.ViewPager2

internal class AustinTabContainerView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {
    private var viewPager2: ViewPager2? = null
    private val data = mutableListOf<TabData>()
    private val listTabItemView = mutableListOf<TabItemView>()
    private val listSpace = mutableListOf<Space>()
    internal var lastIndex: Int = 0
    internal var currentIndex: Int = 0
    internal var animationDuration = 200L

    internal var tabSelectedListener: ((index: Int) -> Unit)? = null
    internal var tabReselectedListener: ((index: Int) -> Unit)? = null
    internal var tabUnSelectedListener: ((index: Int) -> Unit)? = null

    private val lastIndicatorBounds = IndicatorBounds(0, 0)
    private val currentIndicatorBounds = IndicatorBounds(0, 0)
    private val actualIndicatorBounds = IndicatorBounds(0, 0)

    private val animator =
        ValueAnimator.ofObject(IndicatorEvaluator(), lastIndicatorBounds, currentIndicatorBounds)
            .apply {
                duration = animationDuration
                interpolator = LinearInterpolator()
                addUpdateListener {
                    actualIndicatorBounds.copy(it.animatedValue as IndicatorBounds)
                    invalidate()
                }
                addListener(object : AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        updateTabItemView(lastIndex)
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        updateTabItemView(currentIndex)
                        viewPager2?.currentItem = currentIndex
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })
            }

    internal lateinit var tabStyle: TabStyle
    internal lateinit var indicatorStyle: IndicatorStyle
    internal lateinit var indicator: Drawable
    internal var indicatorHeight: Int = 0
    internal var tabPadding: Int = 0
    internal var textColorActive: Int = 0
    internal var textColorInactive: Int = 0
    internal var textFontActive: Int? = null
    internal var textFontInactive: Int? = null
    internal var textSize: Float? = null

    internal fun setData(data: List<TabData>) {
        this.data.clear()
        this.data.addAll(data)
        lastIndex = 0
        currentIndex = 0
        listTabItemView.clear()
        listSpace.clear()
        addViews(initListTabItemView(data))
        for (i in data.indices) {
            updateTabItemView(i)
        }
        this.post {
            lastIndicatorBounds.copy(getIndicatorBounds(0))
            currentIndicatorBounds.copy(getIndicatorBounds(0))
            actualIndicatorBounds.copy(getIndicatorBounds(0))
            invalidate()
        }
    }

    internal fun attachWithPager2(viewPager2: ViewPager2) {
        this.viewPager2 = viewPager2
        this.viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                onTabClicked(position)
                invalidate()
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    init {
        setWillNotDraw(false)
        clipChildren = false
        clipToPadding = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawIndicator(canvas)
    }

    private fun updateTabItemView(index: Int) {
        if (index == currentIndex) {
            listTabItemView[index].tvName.setTextColor(textColorActive)
            if (textFontActive != null) {
                listTabItemView[index].tvName.typeface =
                    ResourcesCompat.getFont(context, textFontActive!!)
            }
        } else {
            listTabItemView[index].tvName.setTextColor(textColorInactive)
            if (textFontInactive != null) {
                listTabItemView[index].tvName.typeface =
                    ResourcesCompat.getFont(context, textFontInactive!!)
            }
        }
    }

    private fun onTabClicked(index: Int) {
        if (index == currentIndex) {
            tabReselectedListener?.invoke(index)
        } else {
            tabSelectedListener?.invoke(index)
            tabUnSelectedListener?.invoke(lastIndex)
        }
        lastIndex = currentIndex
        currentIndex = index
        lastIndicatorBounds.copy(getIndicatorBounds(lastIndex))
        currentIndicatorBounds.copy(getIndicatorBounds(currentIndex))
        startAnimation()
        invalidate()
    }

    private fun startAnimation() {
        animator.duration = if (viewPager2 === null) {
            animationDuration
        } else {
            0L
        }
        animator.start()
    }

    private fun initListTabItemView(data: List<TabData>): List<TabItemView> {
        val list = data.mapIndexed { index, it ->
            val tabItemView = TabItemView(context!!)
            tabItemView.tvNameFake.text = it.name
            if (textFontActive != null) {
                tabItemView.tvNameFake.typeface = ResourcesCompat.getFont(context, textFontActive!!)
            }
            tabItemView.tvName.text = it.name
            tabItemView.tvBadge.text = it.badge
            if (it.badge?.trim().isNullOrEmpty()) {
                tabItemView.tvBadge.visibility = GONE
            } else {
                tabItemView.tvBadge.visibility = VISIBLE
            }
            tabItemView.layoutParams = when (tabStyle) {
                TabStyle.ADAPTIVE -> {
                    LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                TabStyle.EQUAL -> {
                    LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1f
                    )
                }
            }
            if (textSize != null) {
                tabItemView.tvNameFake.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize!!)
                tabItemView.tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize!!)
            }
            tabItemView.setPadding(tabPadding)
            tabItemView.setOnClickListener {
                onTabClicked(index)
            }
            tabItemView
        }
        return list
    }


    private fun addViews(list: List<TabItemView>) {
        when (tabStyle) {
            TabStyle.ADAPTIVE -> {
                list.forEach { tabItemView ->
                    addSpace()
                    addView(tabItemView)
                    listTabItemView.add(tabItemView)
                    addSpace()
                }
            }

            TabStyle.EQUAL -> {
                list.forEach { tabItemView ->
                    addView(tabItemView)
                    listTabItemView.add(tabItemView)
                }

            }
        }
    }

    private fun addSpace() {
        val space = Space(context)
        space.layoutParams = LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
        addView(space)
        listSpace.add(space)
    }

    private fun drawIndicator(canvas: Canvas) {
        val verticalPadding = paddingTop
        when (indicatorStyle) {
            IndicatorStyle.TAB -> {
                indicator.setBounds(
                    actualIndicatorBounds.left,
                    height - indicatorHeight - verticalPadding,
                    actualIndicatorBounds.right,
                    height - verticalPadding
                )
            }

            IndicatorStyle.SEGMENTED_CONTROL -> {
                indicator.setBounds(
                    actualIndicatorBounds.left,
                    verticalPadding,
                    actualIndicatorBounds.right,
                    height - verticalPadding
                )
            }
        }
        indicator.draw(canvas)

    }

    private fun getIndicatorBounds(index: Int): IndicatorBounds {
        return when (tabStyle) {
            TabStyle.ADAPTIVE -> {
                IndicatorBounds(
                    left = listSpace[index * 2].left,
                    right = listSpace[index * 2 + 1].right
                )
            }

            TabStyle.EQUAL -> {
                IndicatorBounds(
                    left = listTabItemView[index].left,
                    right = listTabItemView[index].right
                )
            }
        }
    }
}
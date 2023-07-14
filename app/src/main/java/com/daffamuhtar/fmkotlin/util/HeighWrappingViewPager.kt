package com.daffamuhtar.fmkotlin.util
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class HeightWrappingViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpecUpdated = heightMeasureSpec
        var height = 0

        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            child.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED)
            val childHeight: Int = child.measuredHeight
            if (childHeight > height) {
                height = childHeight
            }
        }

        if (height != 0) {
            heightMeasureSpecUpdated = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpecUpdated)
    }
}

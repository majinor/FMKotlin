package com.daffamuhtar.fmkotlin.util

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.daffamuhtar.fmkotlin.R

class CalendarHelper {
    companion object {
        fun setSelectCalendar(
            context: Context,
            position: Int,
            tvDay: TextView,
            tvDate: TextView,
            tvMonth: TextView,
            lyDate: LinearLayout,
            lyContainer: LinearLayout,
            vIndicator: View
        ) {
            tvDay.setTextColor(ContextCompat.getColorStateList(context, R.color.yellow))
            tvDate.setTextColor(ContextCompat.getColorStateList(context, R.color.yellow))
            tvMonth.setTextColor(ContextCompat.getColorStateList(context, R.color.yellow))
            lyDate.setBackgroundResource(R.drawable.bg_rounded_10dp_black)
            lyContainer.setBackgroundResource(R.drawable.bg_rounded_10dp_blue)
        }

        fun setUnselectCalendar(
            context: Context,
            position: Int,
            tvDay: TextView,
            tvDate: TextView,
            tvMonth: TextView,
            lyDate: LinearLayout,
            lyContainer: LinearLayout,
            vIndicator: View
        ) {
            tvDay.setTextColor(ContextCompat.getColorStateList(context, R.color.grey))
            tvDate.setTextColor(ContextCompat.getColorStateList(context, R.color.grey))
            tvMonth.setTextColor(ContextCompat.getColorStateList(context, R.color.grey))
            lyDate.setBackgroundResource(R.color.background)
            lyContainer.setBackgroundResource(R.color.background)
        }
    }


}
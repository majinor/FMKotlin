package com.daffamuhtar.fmkotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.databinding.ItemHorizontalCalendarBinding
import com.daffamuhtar.fmkotlin.data.model.HorizontalCalendar
import com.daffamuhtar.fmkotlin.util.CalendarHelper
import com.daffamuhtar.fmkotlin.util.CommonHelper

class HorizontalCalendarAdapter(
    private val context: Context,

    ) : RecyclerView.Adapter<HorizontalCalendarAdapter.LaporanViewHolder>() {

    private var items = listOf<HorizontalCalendar>()
    private var maxItem = 0
    private var selectedItemPosition = 1
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItems(allItems: List<HorizontalCalendar>) {
        allItems.let {
            this.items = it
            notifyDataSetChanged()
        }
    }

    fun setSelectedItemPosition(allItems: List<HorizontalCalendar>, maxItem: Int) {
//        allItems.let {
//            this.items = it
//            this.maxItem = maxItem
//            notifyDataSetChanged()
//        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: HorizontalCalendar, position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class LaporanViewHolder(private val view: ItemHorizontalCalendarBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: HorizontalCalendar, position: Int) {
            with(view) {

                tvMonth.text = CommonHelper.getMonth(item.date)
                tvDate.text = CommonHelper.getDate(item.date)
                tvDay.text = CommonHelper.getDay(item.date)

                tvDate.setTypeface(ResourcesCompat.getFont(context, R.font.messinasans_black))
                tvDate.setTextAppearance(context, R.style.text_17_extra_bold)

                if (item.counterBadge > 0) {
                    vIndicator.visibility = View.VISIBLE
                } else {
                    vIndicator.visibility = View.INVISIBLE
                }

                if (selectedItemPosition == position) {
                    CalendarHelper.setSelectCalendar(
                        context,
                        position,
                        tvDay,
                        tvDate,
                        tvMonth,
                        lyDate,
                        lyContainer,
                        vIndicator
                    )
                } else {
                    CalendarHelper.setUnselectCalendar(
                        context,
                        position,
                        tvDay,
                        tvDate,
                        tvMonth,
                        lyDate,
                        lyContainer,
                        vIndicator
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val view = ItemHorizontalCalendarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LaporanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
//        if (position<=maxItem) {
        holder.bind(items[position], position)

//        }
//        if (!items[position].isEditable) {
//            holder.itemView.setOnClickListener {
//                onItemClickCallback.onItemClicked(items[holder.adapterPosition], position)
//            }
//        } else {
//            holder.itemView.isClickable = false
//        }
    }

    override fun getItemCount(): Int = items.size
}
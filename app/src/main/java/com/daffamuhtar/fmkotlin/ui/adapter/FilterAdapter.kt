package com.daffamuhtar.fmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.databinding.ItemFilterBinding
import com.daffamuhtar.fmkotlin.data.Filter

class FilterAdapter() : RecyclerView.Adapter<FilterAdapter.LaporanViewHolder>() {

    private var allFilter = listOf<Filter>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setAllFilter(allFilter: List<Filter>) {
        allFilter.let {
            this.allFilter = it
            notifyDataSetChanged()
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: Filter,position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class LaporanViewHolder(private val view: ItemFilterBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: Filter) {
            with(view) {

                if (item.isActive) {
                    lyIrsfBackground.setBackgroundResource(R.drawable.bg_button_rounded_yellow)
                    tvIrsfStatus.setTextColor(ContextCompat.getColor(root.context, R.color.black))
                } else {
                    lyIrsfBackground.setBackgroundResource(R.drawable.bg_button_rounded_outline_grey)
                    tvIrsfStatus.setTextColor(ContextCompat.getColor(root.context, R.color.grey))
                }

                tvIrsfStatus.text = item.stageName


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val view = ItemFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LaporanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        holder.bind(allFilter[position])

        if(!allFilter[position].isActive) {
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(allFilter[holder.adapterPosition], position)
            }
        }else{
            holder.itemView.isClickable = false
        }
    }

    override fun getItemCount(): Int = allFilter.size
}
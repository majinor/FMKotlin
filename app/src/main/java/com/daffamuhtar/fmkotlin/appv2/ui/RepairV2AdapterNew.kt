package com.daffamuhtar.fmkotlin.appv2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.daffamuhtar.fmkotlin.appv4.ui.view_binding.RepairListViewHolderNew
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.databinding.ItemRepairBinding

class RepairV2AdapterNew constructor() :
    PagingDataAdapter<Repair, RepairListViewHolderNew>(Comparator) {

    override fun onBindViewHolder(holder: RepairListViewHolderNew, position: Int) {
        holder.bind(getItem(position)!!,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairListViewHolderNew {
        return RepairListViewHolderNew(
            ItemRepairBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), null
        )
    }

    object Comparator : DiffUtil.ItemCallback<Repair>() {

        override fun areItemsTheSame(oldItem: Repair, newItem: Repair): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Repair, newItem: Repair): Boolean {
            return oldItem == newItem
        }
    }
}



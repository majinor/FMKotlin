package com.daffamuhtar.fmkotlin.appv4.ui.view_binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.databinding.ItemRepairBinding

/**
 * Created by Oguz Sahin on 11/10/2021.
 */
class RepairAdapterNew constructor() :
    PagingDataAdapter<Repair, RepairListViewHolderNew>(Comparator) {

    override fun onBindViewHolder(holder: RepairListViewHolderNew, position: Int) {
        holder.bind(getItem(position)!!,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairListViewHolderNew {
        return RepairListViewHolderNew(
            ItemRepairBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
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



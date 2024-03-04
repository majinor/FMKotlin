package com.daffamuhtar.fmkotlin.appv4.ui.data_binding


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.appv4.ui.view_binding.RepairListViewHolderNew
import com.daffamuhtar.fmkotlin.databinding.ItemRepairBinding
import com.daffamuhtar.fmkotlin.databinding.ItemRepairNew4Binding

/**
 * Created by Oguz Sahin on 11/10/2021.
 */
class RepairAdapter constructor() :
    PagingDataAdapter<RepairItemUiState, RepairListViewHolder>(Comparator) {

    override fun onBindViewHolder(holder: RepairListViewHolder, position: Int) {
        getItem(position)?.let { repairItemUiState -> holder.bind(repairItemUiState) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairListViewHolder {

        val binding = ItemRepairNew4Binding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return RepairListViewHolder(binding, binding.root.context)

    }

    object Comparator : DiffUtil.ItemCallback<RepairItemUiState>() {
        override fun areItemsTheSame(
            oldItem: RepairItemUiState,
            newItem: RepairItemUiState
        ): Boolean {
            return oldItem.getRepairId() == newItem.getRepairId()
        }

        override fun areContentsTheSame(
            oldItem: RepairItemUiState,
            newItem: RepairItemUiState
        ): Boolean {
            return oldItem == newItem
        }
    }

}
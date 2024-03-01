package com.daffamuhtar.fmkotlin.appv4.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.appv4.RepairResponse4
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.databinding.ItemRepairBinding
import com.daffamuhtar.fmkotlin.databinding.ItemRepairNew4Binding
import com.daffamuhtar.fmkotlin.ui.adapter.MoviePosterViewHolder

/**
 * Created by Oguz Sahin on 11/10/2021.
 */
class RepairAdapterNew constructor() :
    PagingDataAdapter<Repair, RepairListViewHolder>(MovieDiffCallBack()) {

    override fun onBindViewHolder(holder: RepairListViewHolder, position: Int) {
        holder.bind(getItem(position)!!,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairListViewHolder {
        return RepairListViewHolder(
            ItemRepairBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object Comparator : DiffUtil.ItemCallback<UserItemUiState>() {
        override fun areItemsTheSame(oldItem: UserItemUiState, newItem: UserItemUiState): Boolean {
            return oldItem.getPhone() == newItem.getPhone()
        }

        override fun areContentsTheSame(
            oldItem: UserItemUiState,
            newItem: UserItemUiState
        ): Boolean {
            return oldItem == newItem
        }
    }

}

class MovieDiffCallBack : DiffUtil.ItemCallback<Repair>() {
    override fun areItemsTheSame(oldItem: Repair, newItem: Repair): Boolean {
        return oldItem.orderId == newItem.orderId
    }

    override fun areContentsTheSame(oldItem: Repair, newItem: Repair): Boolean {
        return oldItem == newItem
    }
}
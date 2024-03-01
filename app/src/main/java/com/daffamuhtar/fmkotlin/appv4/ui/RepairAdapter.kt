package com.daffamuhtar.fmkotlin.appv4.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.databinding.ItemRepairNew4Binding

/**
 * Created by Oguz Sahin on 11/10/2021.
 */
class RepairAdapter constructor() :
    PagingDataAdapter<UserItemUiState, UserViewHolder>(Comparator) {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { userItemUiState -> holder.bind(userItemUiState) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val binding = inflate<ItemRepairNew4Binding>(
            LayoutInflater.from(parent.context),
            R.layout.item_repair_new4,
            parent,
            false
        )

        return UserViewHolder(binding)
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
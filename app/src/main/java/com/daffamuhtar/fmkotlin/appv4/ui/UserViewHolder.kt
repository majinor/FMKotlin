package com.daffamuhtar.fmkotlin.appv4.ui

import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.appv4.util.executeWithAction
import com.daffamuhtar.fmkotlin.databinding.ItemRepairBinding
import com.daffamuhtar.fmkotlin.databinding.ItemRepairNew4Binding

/**
 * Created by Oguz Sahin on 11/11/2021.
 */
class UserViewHolder(private val binding: ItemRepairNew4Binding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(userItemUiState: UserItemUiState) {
        binding.executeWithAction {
            this.userItemUiState = userItemUiState
        }
    }
}
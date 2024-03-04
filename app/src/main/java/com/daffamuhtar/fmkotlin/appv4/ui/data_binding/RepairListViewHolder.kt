package com.daffamuhtar.fmkotlin.appv4.ui.data_binding

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.appv4.util.executeWithAction
import com.daffamuhtar.fmkotlin.databinding.ItemRepairNew4Binding

/**
 * Created by Oguz Sahin on 11/11/2021.
 */
class RepairListViewHolder(private val binding: ItemRepairNew4Binding, context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(repairItemUiState: RepairItemUiState) {
        binding.executeWithAction {
            this.repairItemUiState = repairItemUiState
        }
    }
}
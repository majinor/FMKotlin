package com.daffamuhtar.fmkotlin.appv4.common

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.appv4.common.FooterUiState
import com.daffamuhtar.fmkotlin.appv4.util.executeWithAction
import com.daffamuhtar.fmkotlin.databinding.ItemPagingFooterBinding

/**
 * Created by Oguz Sahin on 11/11/2021.
 */
class FooterViewHolder(
    private val binding: ItemPagingFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.executeWithAction {
            footerUiState = FooterUiState(loadState)
        }
    }
}


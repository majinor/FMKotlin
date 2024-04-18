package com.daffamuhtar.fmkotlin.appv2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.daffamuhtar.fmkotlin.appv2.data.local.RepairEntity
import com.daffamuhtar.fmkotlin.appv2.data.mapper.toRepair
import kotlinx.coroutines.flow.map

class RepairV2ViewModel(
    pager: Pager<Int, RepairEntity>
): ViewModel() {

    val beerPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toRepair() }
        }
        .cachedIn(viewModelScope)
}
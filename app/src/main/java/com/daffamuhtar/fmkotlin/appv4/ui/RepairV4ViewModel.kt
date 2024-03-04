package com.daffamuhtar.fmkotlin.appv4.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.daffamuhtar.fmkotlin.appv4.RepairRepository4
import com.daffamuhtar.fmkotlin.appv4.ui.data_binding.RepairItemUiState
import com.daffamuhtar.fmkotlin.util.RepairHelper
import kotlinx.coroutines.flow.map

class RepairV4ViewModel (
    repairRepository4: RepairRepository4,
    context: Context
) : ViewModel(){

    //data binding (ui state)

//    val repairPagingData = repairRepository4.getRepairList()
//        .map { pagingData ->
//            pagingData.map { userModel -> RepairItemUiState(RepairHelper.mapRepairItem(userModel), context) }
//        }.cachedIn(viewModelScope)

    //view binding
    val repairPagingData = repairRepository4.getRepairList()
        .map { pagingData ->
            pagingData.map { userModel -> RepairHelper.mapRepairItem(userModel)}
        }.cachedIn(viewModelScope)

}
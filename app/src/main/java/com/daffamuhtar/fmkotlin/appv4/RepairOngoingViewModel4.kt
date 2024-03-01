package com.daffamuhtar.fmkotlin.appv4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.daffamuhtar.fmkotlin.appv4.ui.UserItemUiState
import kotlinx.coroutines.flow.map


class RepairOngoingViewModel4 constructor(repairRepository4: RepairRepository4) : ViewModel() {
    val userItemsUiStates = repairRepository4.getUsers()
        .map { pagingData ->
            pagingData.map { userModel -> UserItemUiState(userModel) }
        }.cachedIn(viewModelScope)
}
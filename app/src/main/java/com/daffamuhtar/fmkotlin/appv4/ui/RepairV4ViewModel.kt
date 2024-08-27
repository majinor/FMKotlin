package com.daffamuhtar.fmkotlin.appv4.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.daffamuhtar.fmkotlin.appv4.RepairRepository4
import com.daffamuhtar.fmkotlin.appv4.ui.data_binding.RepairItemUiState
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.data.response.RepairCheckResponse
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.daffamuhtar.fmkotlin.util.Resource
import kotlinx.coroutines.flow.map

class RepairV4ViewModel (
    repairRepository4: RepairRepository4,
    context: Context
) : ViewModel(){

    //data binding (ui state)

    val repairPagingData = repairRepository4.getRepairList()
        .map { pagingData ->
            pagingData.map { userModel -> RepairItemUiState(RepairHelper.mapRepairItem(userModel), context) }
        }.cachedIn(viewModelScope)

    //view binding
//    val repairPagingData = repairRepository4.getRepairList()
//        .map { pagingData ->
//            pagingData.map { userModel -> RepairHelper.mapRepairItem(userModel)}
//        }.cachedIn(viewModelScope)


    val dogBreeds: MutableLiveData<List<Repair>> = MutableLiveData()
    val hehe: MutableLiveData<String> = MutableLiveData()

    fun fetchDogBreeds(count : Int) {
        // Fetch the data from an API or a local database
        // For simplicity, let's assume we have a hardcoded list of breeds


        // Update the MutableLiveData with the new list of dog breeds
        if (hehe.value == "gantii"){
            hehe.value =  "Ini dia"
        }else{
            hehe.value = "gantii"
        }
    }
}
package com.daffamuhtar.fmkotlin.ui.repair_tab

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map

import com.daffamuhtar.fmkotlin.appv4.RepairRepository4
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.util.RepairHelper
import kotlinx.coroutines.flow.map

class RepairTaskTireFragmentViewModel (
    repairRepository4: RepairRepository4,
    context: Context
): ViewModel() {

    val repairPagingData = repairRepository4.getRepairListTire()
        .map { pagingData ->
            pagingData.map { userModel -> RepairHelper.mapRepairItem(userModel)}
        }.cachedIn(viewModelScope)


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

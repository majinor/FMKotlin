package com.daffamuhtar.fmkotlin.ui.repair_on

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map

import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.appv2.data.local.RepairEntity
import com.daffamuhtar.fmkotlin.appv2.data.mapper.toRepair
import com.daffamuhtar.fmkotlin.data.response.RepairOnAdhocResponse
import com.daffamuhtar.fmkotlin.data.response.ErrorResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.map
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class RepairOngoingAdhocViewCopyModel(
    pager: Pager<Int, RepairEntity>
) : ViewModel() {

    val repairPaging = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toRepair() }
        }
        .cachedIn(viewModelScope)
}
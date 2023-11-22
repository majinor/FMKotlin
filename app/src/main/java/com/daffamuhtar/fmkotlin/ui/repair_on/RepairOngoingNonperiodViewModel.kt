package com.daffamuhtar.fmkotlin.ui.repair_on

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffamuhtar.fmkotlin.constants.ConstantsApp

import com.daffamuhtar.fmkotlin.data.repository_old.RepairOnNonperiodRepository
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.daffamuhtar.fmkotlin.util.NetworkHelper
import com.daffamuhtar.fmkotlin.util.OldResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException

class RepairOngoingNonperiodViewModel(
    private val repairOnNonperiodRepository: RepairOnNonperiodRepository,
    private val networkHelper: NetworkHelper,
    private val retrofitBlogV1: Retrofit,
    private val retrofitBlogV2: Retrofit,
    private val retrofitBlogV2Rep: Retrofit,
    private val retrofitInternalVendorV1: Retrofit,
) : ViewModel() {

    private val _repairList = MutableLiveData<OldResource<List<com.daffamuhtar.fmkotlin.data.remote.response.RepairOnNonperiodResponse>>>()
    val repairList: LiveData<OldResource<List<com.daffamuhtar.fmkotlin.data.remote.response.RepairOnNonperiodResponse>>> get() = _repairList

    fun getRepairNonperiod(
        context: Context,
        apiVersion: String,
        userId: String,
    ) {
        viewModelScope.launch {

            val retrofit =
                when (apiVersion) {
                    ConstantsApp.BASE_URL_V1_0 -> {
                        retrofitBlogV1
                    }
                    ConstantsApp.BASE_URL_V2_0 -> {
                        retrofitBlogV2
                    }
                    ConstantsApp.BASE_URL_V2_0_REP -> {
                        retrofitBlogV2Rep
                    }
                    ConstantsApp.BASE_URL2 -> {
                        retrofitInternalVendorV1
                    }
                    else -> {
                        retrofitBlogV1
                    }
            }
            val services = retrofitBlogV2.create(RepairServices::class.java)

            _repairList.postValue(OldResource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repairOnNonperiodRepository.getRepairOnNonperiod(services,userId).let {
                    if (it.isSuccessful) {
                        _repairList.postValue(OldResource.success(it.body(), it.code()))
                    } else {

                        val responseErrorBody = it.errorBody()
                        var errorModel: com.daffamuhtar.fmkotlin.data.remote.response.ErrorResponse? = null

                        if (responseErrorBody != null) {
                            Log.w(
                                "RESULT:",
                                "onResponse: Not Success HEHE" + it.code() + GsonBuilder().setPrettyPrinting()
                                    .create().toJson(responseErrorBody)
                            )
                            val converter: Converter<ResponseBody?, com.daffamuhtar.fmkotlin.data.remote.response.ErrorResponse> =
                                retrofit.responseBodyConverter(
                                    com.daffamuhtar.fmkotlin.data.remote.response.ErrorResponse::class.java,
                                    arrayOfNulls<Annotation>(0)
                                )
                            try {
                                errorModel = converter.convert(responseErrorBody)
                            } catch (e: IOException) {
                                e.printStackTrace()
                                Log.d("TAG", "onResponse: $e")
                            }
                        }

                        _repairList.postValue(
                            OldResource.error(
                                Gson().toJson(errorModel).toString(), null, it.code()
                            )
                        )
                    }
                }
            } else _repairList.postValue(OldResource.error("No internet connection", null, 0))
        }
    }

}

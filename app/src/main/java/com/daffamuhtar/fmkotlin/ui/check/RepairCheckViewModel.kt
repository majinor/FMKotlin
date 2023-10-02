package com.daffamuhtar.fmkotlin.ui.check

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.constants.Constants
import com.daffamuhtar.fmkotlin.data.repository.MainRepository
import com.daffamuhtar.fmkotlin.data.response.RepairCheckResponse
import com.daffamuhtar.fmkotlin.data.response.ErrorResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.daffamuhtar.fmkotlin.util.NetworkHelper
import com.daffamuhtar.fmkotlin.util.Resource
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class RepairCheckViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _repairList = MutableLiveData<Resource<List<RepairCheckResponse>>>()
    val repairList: LiveData<Resource<List<RepairCheckResponse>>> get() = _repairList


//    private val _isLoadingGetAllCheckRepair = MutableLiveData<Boolean>()
//    val isLoadingGetAllCheckRepair: LiveData<Boolean> = _isLoadingGetAllCheckRepair
//
//    private val _isSuccessGetAllCheckRepair = MutableLiveData<Boolean>()
//    val isSuccessGetAllCheckRepair: LiveData<Boolean> = _isSuccessGetAllCheckRepair
//
//    private val _messageGetAllCheckRepair = MutableLiveData<String>()
//    val messageGetAllCheckRepair: LiveData<String> = _messageGetAllCheckRepair

    fun getAllCheckRepair(
        userId: String,
    ) {
        viewModelScope.launch {
            _repairList.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getCheckRepair(userId).let {
                    if (it.isSuccessful) {
                        _repairList.postValue(Resource.success(it.body()))
                    } else _repairList.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _repairList.postValue(Resource.error("No internet connection", null))
        }
    }

//    fun getAllCheckRepair(
//        context: Context,
//        apiVersion: String,
//        userId: String,
//    ) {
//        _isLoadingGetAllCheckRepair.value = true
//
//        val retrofit = ApiConfig.getRetrofit(context, apiVersion)
//        val services = retrofit?.create(RepairServices::class.java)
//        val client = services?.getCheckRepair(userId, 0)
//
//        client?.enqueue(object : Callback<List<RepairCheckResponse>> {
//            override fun onResponse(
//                call: Call<List<RepairCheckResponse>>,
//                response: Response<List<RepairCheckResponse>>
//            ) {
//                _isLoadingGetAllCheckRepair.value = false
//
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    Log.w(
//                        "RESULT:",
//                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
//                    )
//
//                    val repair: List<RepairCheckResponse>? = response.body()
//                    if (responseBody != null) {
//                        _repairList.value = repair!!
//                    }
//
//                } else {
//                    val responseErrorBody = response.errorBody()
//                    if (responseErrorBody != null) {
//                        Log.w(
//                            "RESULT:",
//                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
//                                .create().toJson(responseErrorBody)
//                        )
//                        val converter: Converter<ResponseBody?, ErrorResponse> =
//                            retrofit.responseBodyConverter(
//                                ErrorResponse::class.java,
//                                arrayOfNulls<Annotation>(0)
//                            )
//                        var errorModel: ErrorResponse? = null
//                        try {
//                            errorModel = converter.convert(responseErrorBody)
//                            val status: Boolean = errorModel?.status ?: false
//                            val message: String = errorModel?.message ?: "no message"
//
//                            _isSuccessGetAllCheckRepair.value = status
//                            _messageGetAllCheckRepair.value = message
//
//                            Toast.makeText(context, "Gagal Mengirim $message", Toast.LENGTH_SHORT)
//                                .show()
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                            Log.d("TAG", "onResponse: $e")
//                        }
//                    }
//
//                }
//
//            }
//
//            override fun onFailure(call: Call<List<RepairCheckResponse>>, t: Throwable) {
//                _isLoadingGetAllCheckRepair.value = false
//                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
//                _messageGetAllCheckRepair.value =
//                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
//            }
//        })
//    }

}
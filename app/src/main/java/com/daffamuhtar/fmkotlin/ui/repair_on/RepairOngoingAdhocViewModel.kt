package com.daffamuhtar.fmkotlin.ui.repair_on

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.app.Resource
import com.daffamuhtar.fmkotlin.data.remote.response.RepairOnNonperiodResponse
import com.daffamuhtar.fmkotlin.domain.use_case.GetRepairAdhocListUseCase
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.onEach
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class RepairOngoingAdhocViewModel constructor(
    private val getCoinsUseCase: GetRepairAdhocListUseCase
): ViewModel() {

    private val _isLoadingGetRepair = MutableLiveData<Boolean>()
    val isLoadingGetRepair: LiveData<Boolean> = _isLoadingGetRepair

    private val _isSuccessGetRepair = MutableLiveData<Boolean>()
    val isSuccessGetRepair: LiveData<Boolean> = _isSuccessGetRepair

    private val _messageGetRepair = MutableLiveData<String>()
    val messageGetRepair: LiveData<String> = _messageGetRepair

    private val _repairList = MutableLiveData<Resource<List<RepairOnNonperiodResponse>>>()
    val repairList: LiveData<Resource<List<RepairOnNonperiodResponse>>> get() = _repairList

    init {
        getCoins("ss",1,"sds")
    }

    fun getAllLaporan(userId : String, stageId : Int, searchQuery :String) = getCoinsUseCase(userId, stageId, searchQuery)

    private fun getCoins(userId : String, stageId : Int, searchQuery :String) {
        getCoinsUseCase(userId, stageId, searchQuery).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = CoinListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getRepairAdhoc(
        context: Context,
        apiVersion: String,
        userId: String,
    ) {
        _isLoadingGetRepair.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)
        val services = retrofit?.create(RepairServices::class.java)
        val client = services?.getRepairAdhoc(userId, 0)

        client?.enqueue(object : Callback<List<com.daffamuhtar.fmkotlin.data.remote.response.RepairOnAdhocResponse>> {
            override fun onResponse(
                call: Call<List<com.daffamuhtar.fmkotlin.data.remote.response.RepairOnAdhocResponse>>,
                response: Response<List<com.daffamuhtar.fmkotlin.data.remote.response.RepairOnAdhocResponse>>
            ) {
                _isLoadingGetRepair.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val repair: List<com.daffamuhtar.fmkotlin.data.remote.response.RepairOnAdhocResponse>? = response.body()
                    if (responseBody != null) {
                        _repairList.value = repair!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, com.daffamuhtar.fmkotlin.data.remote.response.ErrorResponse> =
                            retrofit.responseBodyConverter(
                                com.daffamuhtar.fmkotlin.data.remote.response.ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: com.daffamuhtar.fmkotlin.data.remote.response.ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepair.value = status
                            _messageGetRepair.value = message

                            Toast.makeText(context, "Gagal Mengirim $message", Toast.LENGTH_SHORT)
                                .show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<com.daffamuhtar.fmkotlin.data.remote.response.RepairOnAdhocResponse>>, t: Throwable) {
                _isLoadingGetRepair.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepair.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }
    
}
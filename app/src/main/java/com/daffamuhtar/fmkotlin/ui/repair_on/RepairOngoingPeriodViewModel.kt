package com.daffamuhtar.fmkotlin.ui.repair_on

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.data.response.RepairOnPeriodResponse
import com.daffamuhtar.fmkotlin.data.response.ErrorResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class RepairOngoingPeriodViewModel : ViewModel() {

    private val _isLoadingGetRepair = MutableLiveData<Boolean>()
    val isLoadingGetRepair: LiveData<Boolean> = _isLoadingGetRepair

    private val _isSuccessGetRepair = MutableLiveData<Boolean>()
    val isSuccessGetRepair: LiveData<Boolean> = _isSuccessGetRepair

    private val _messageGetRepair = MutableLiveData<String>()
    val messageGetRepair: LiveData<String> = _messageGetRepair

    private val _repairList = MutableLiveData<List<RepairOnPeriodResponse>>()
    val repairList: LiveData<List<RepairOnPeriodResponse>> = _repairList

    fun getRepairPeriod(
        context: Context,
        apiVersion: String,
        userId: String,
    ) {
        _isLoadingGetRepair.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)
        val services = retrofit?.create(RepairServices::class.java)
        val client = services?.getRepairPeriod(userId, 0)

        client?.enqueue(object : Callback<List<RepairOnPeriodResponse>> {
            override fun onResponse(
                call: Call<List<RepairOnPeriodResponse>>,
                response: Response<List<RepairOnPeriodResponse>>
            ) {
                _isLoadingGetRepair.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val repair: List<RepairOnPeriodResponse>? = response.body()
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
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
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

            override fun onFailure(call: Call<List<RepairOnPeriodResponse>>, t: Throwable) {
                _isLoadingGetRepair.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepair.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }
    
}
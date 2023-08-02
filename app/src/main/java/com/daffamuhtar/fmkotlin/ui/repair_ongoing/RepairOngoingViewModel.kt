package com.daffamuhtar.fmkotlin.ui.Ongoing

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.VolleyLog
import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.model.response.ErrorResponse
import com.daffamuhtar.fmkotlin.model.response.RepairOnAdhocResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class RepairOngoingViewModel : ViewModel() {

    private val _isLoadingGetRepairOngoing = MutableLiveData<Boolean>()
    val isLoadingGetRepairOngoing: LiveData<Boolean> = _isLoadingGetRepairOngoing

    private val _isSuccessGetRepairOngoing = MutableLiveData<Boolean>()
    val isSuccessGetRepairOngoing: LiveData<Boolean> = _isSuccessGetRepairOngoing

    private val _messageGetRepairOngoing = MutableLiveData<String>()
    val messageGetRepairOngoing: LiveData<String> = _messageGetRepairOngoing

    private val _repairList = MutableLiveData<List<RepairOnAdhocResponse>>()
    val repairList: LiveData<List<RepairOnAdhocResponse>> = _repairList


    fun getRepairOngoing(
        context: Context,
        apiVersion: String,
        userId: String,
    ) {
        _isLoadingGetRepairOngoing.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)
        val services = retrofit?.create(RepairServices::class.java)
        val client = services?.getRepairOngoing(userId, 0)

        client?.enqueue(object : Callback<List<RepairOnAdhocResponse>> {
            override fun onResponse(
                call: Call<List<RepairOnAdhocResponse>>,
                response: Response<List<RepairOnAdhocResponse>>
            ) {
                _isLoadingGetRepairOngoing.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val repair: List<RepairOnAdhocResponse>? = response.body()
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

                            _isSuccessGetRepairOngoing.value = status
                            _messageGetRepairOngoing.value = message

                            Toast.makeText(context, "Gagal Mengirim $message", Toast.LENGTH_SHORT)
                                .show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d(VolleyLog.TAG, "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairOnAdhocResponse>>, t: Throwable) {
                _isLoadingGetRepairOngoing.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairOngoing.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

}
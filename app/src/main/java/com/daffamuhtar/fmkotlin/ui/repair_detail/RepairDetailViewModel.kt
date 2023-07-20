package com.daffamuhtar.fmkotlin.ui.repair_detail

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.VolleyLog
import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.model.response.RepairProblemResponse
import com.daffamuhtar.fmkotlin.model.response.ErrorResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.daffamuhtar.fmkotlin.ui.check.CheckActivity
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class RepairDetailViewModel : ViewModel() {
    private val _isLoadingGetAllRepairProblem = MutableLiveData<Boolean>()
    val isLoadingGetAllRepairProblem: LiveData<Boolean> = _isLoadingGetAllRepairProblem

    private val _isSuccessGetAllRepairProblem = MutableLiveData<Boolean>()
    val isSuccessGetAllRepairProblem: LiveData<Boolean> = _isSuccessGetAllRepairProblem

    private val _messageGetAllRepairProblem = MutableLiveData<String>()
    val messageGetAllRepairProblem: LiveData<String> = _messageGetAllRepairProblem

    private val _problemList = MutableLiveData<List<RepairProblemResponse>>()
    val problemList: LiveData<List<RepairProblemResponse>> = _problemList

    fun getAllRepairProblem(
        context: Context,
        apiVersion: String,
        orderId: String,
    ) {
        _isLoadingGetAllRepairProblem.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)
        val services = retrofit?.create(RepairServices::class.java)
        val client = services?.getRepairProblem(orderId)

        client?.enqueue(object : Callback<List<RepairProblemResponse>> {
            override fun onResponse(
                call: Call<List<RepairProblemResponse>>,
                response: Response<List<RepairProblemResponse>>
            ) {
                _isLoadingGetAllRepairProblem.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val repair: List<RepairProblemResponse>? = response.body()
                    if (responseBody != null) {
                        _problemList.value = repair!!
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

                            _isSuccessGetAllRepairProblem.value = status
                            _messageGetAllRepairProblem.value = message

                            Toast.makeText(context, "Gagal Mengirim $message", Toast.LENGTH_SHORT)
                                .show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d(VolleyLog.TAG, "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairProblemResponse>>, t: Throwable) {
                _isLoadingGetAllRepairProblem.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetAllRepairProblem.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }
}

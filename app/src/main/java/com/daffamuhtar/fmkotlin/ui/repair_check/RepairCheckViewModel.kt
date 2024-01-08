package com.daffamuhtar.fmkotlin.ui.repair_check

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.app.Server
import com.daffamuhtar.fmkotlin.constants.ConstantsApp

import com.daffamuhtar.fmkotlin.data.repository.RepairCheckRepository
import com.daffamuhtar.fmkotlin.data.response.RepairCheckResponse
import com.daffamuhtar.fmkotlin.data.response.ErrorResponse
import com.daffamuhtar.fmkotlin.data.response.RefreshTokenResponse
import com.daffamuhtar.fmkotlin.services.AccountServices
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.daffamuhtar.fmkotlin.util.DynamicRetrofit
import com.daffamuhtar.fmkotlin.util.NetworkHelper
import com.daffamuhtar.fmkotlin.util.Resource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class RepairCheckViewModel(
    private val repairCheckRepository: RepairCheckRepository,
    private val networkHelper: NetworkHelper,
    private val retrofitBlogV1: Retrofit,
    private val retrofitBlogV2: Retrofit,
    private val retrofitBlogV2Rep: Retrofit,
    private val retrofitInternalVendorV1: Retrofit,
    private val dynamicRetrofit: DynamicRetrofit
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
        context: Context,
        apiVersion: String,
        userId: String,
    ) {
        viewModelScope.launch {

            dynamicRetrofit.setUrl(Server.URL1)

            val retrofit =
//                when (apiVersion) {
//                    ConstantsApp.BASE_URL_V1_0 -> {
//                        retrofitBlogV1
//                    }
//                    ConstantsApp.BASE_URL_V2_0 -> {
//                        retrofitBlogV2
//                    }
//                    ConstantsApp.BASE_URL_V2_0_REP -> {
//                        retrofitBlogV2Rep
//                    }
//                    ConstantsApp.BASE_URL2 -> {
//                        retrofitInternalVendorV1
//                    }
//                    else -> {
//                        retrofitBlogV1
                        dynamicRetrofit.retrofit
//                    }
//                }


            val services = retrofit.create(RepairServices::class.java)

            _repairList.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {

                repairCheckRepository.getCheckRepair(services,userId).let {
                    if (it.isSuccessful) {
                        _repairList.postValue(Resource.success(it.body(), it.code()))
                    } else {

                        val responseErrorBody = it.errorBody()
                        var errorModel: ErrorResponse? = null

                        if (responseErrorBody != null) {
                            Log.w(
                                "RESULT:",
                                "onResponse: Not Success HEHE" + it.code() + GsonBuilder().setPrettyPrinting()
                                    .create().toJson(responseErrorBody)
                            )
                            val converter: Converter<ResponseBody?, ErrorResponse> =
                                this@RepairCheckViewModel.retrofitBlogV1.responseBodyConverter(
                                    ErrorResponse::class.java,
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
                            Resource.error(
                                Gson().toJson(errorModel).toString(), null, it.code()
                            )
                        )
                    }
                }
            } else _repairList.postValue(Resource.error("No internet connection", null, 0))
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


    private val _isLoadingRefreshToken = MutableLiveData<Boolean>()
    val isLoadingRefreshToken: LiveData<Boolean> = _isLoadingRefreshToken

    private val _isSuccessRefreshToken = MutableLiveData<Boolean>()
    val isSuccessRefreshToken: LiveData<Boolean> = _isSuccessRefreshToken

    private val _messageRefreshToken = MutableLiveData<String>()
    val messageRefreshToken: LiveData<String> = _messageRefreshToken

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun postRefreshToken(
        context: Context,
        apiVersion: String,
        userId: String,
        currentAppVersion: String,
        appType: String
    ) {
        _isLoadingRefreshToken.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)
        val accountServices = retrofit?.create(AccountServices::class.java)
        val client = accountServices?.postRefreshToken(userId, currentAppVersion, appType)

        client?.enqueue(object : Callback<RefreshTokenResponse> {
            override fun onResponse(
                call: Call<RefreshTokenResponse>,
                response: Response<RefreshTokenResponse>
            ) {
                _isLoadingRefreshToken.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _isSuccessRefreshToken.value = true
                    _messageRefreshToken.value = responseBody?.message
                    _token.value = responseBody?.token

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, RefreshTokenResponse> =
                            retrofit.responseBodyConverter(
                                RefreshTokenResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: RefreshTokenResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessRefreshToken.value = status
                            _messageRefreshToken.value = message

                            Toast.makeText(context, "Gagal Mengirim $message", Toast.LENGTH_SHORT)
                                .show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                _isLoadingRefreshToken.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageRefreshToken.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }
}
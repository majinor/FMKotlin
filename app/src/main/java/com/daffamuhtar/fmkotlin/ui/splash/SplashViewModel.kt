package com.daffamuhtar.fmkotlin.ui.splash

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.VolleyLog
import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.model.response.RefreshTokenResponse
import com.daffamuhtar.fmkotlin.services.AccountServices
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class SplashViewModel : ViewModel() {


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
                            Log.d(VolleyLog.TAG, "onResponse: $e")
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
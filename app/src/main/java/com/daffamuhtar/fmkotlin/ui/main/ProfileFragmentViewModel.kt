package com.daffamuhtar.fmkotlin.ui.main

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.appv4.RepairRepository4
import com.daffamuhtar.fmkotlin.constants.ConstantsRepair
import com.daffamuhtar.fmkotlin.data.model.Profile
import com.daffamuhtar.fmkotlin.data.response.ErrorResponse
import com.daffamuhtar.fmkotlin.data.response.ProfileResponse
import com.daffamuhtar.fmkotlin.data.response.RepairDetailProblemResponse
import com.daffamuhtar.fmkotlin.services.AccountServices
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.daffamuhtar.fmkotlin.util.AccountHelper
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class ProfileFragmentViewModel(
    context: Context
) : ViewModel() {

    private val _isLoadingGetProfile = MutableLiveData<Boolean>()
    val isLoadingGetProfile: LiveData<Boolean> = _isLoadingGetProfile

    private val _isSuccessGetProfile = MutableLiveData<Boolean>()
    val isSuccessGetProfile: LiveData<Boolean> = _isSuccessGetProfile

    private val _messageGetProfile = MutableLiveData<String>()
    val messageGetProfile: LiveData<String> = _messageGetProfile

    private val _profile = MutableLiveData<Profile?>()
    val profile: MutableLiveData<Profile?> = _profile


    fun getProfile(
        context: Context,
        apiVersion: String,
        userId: String,
    ) {
        _isLoadingGetProfile.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(AccountServices::class.java)
        var client = services.getProfile(userId)

        client.enqueue(object : Callback<List<ProfileResponse>> {
            override fun onResponse(
                call: Call<List<ProfileResponse>>,
                response: Response<List<ProfileResponse>>
            ) {
                _isLoadingGetProfile.value = false

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _profile.value = AccountHelper.mapProfile(response.body()!!)
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

                            _isSuccessGetProfile.value = status
                            _messageGetProfile.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")

                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<ProfileResponse>>, t: Throwable) {
                _isLoadingGetProfile.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetProfile.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

}
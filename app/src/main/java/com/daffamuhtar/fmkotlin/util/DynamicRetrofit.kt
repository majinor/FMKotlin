package com.daffamuhtar.fmkotlin.util

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DynamicRetrofit(private val gson: Gson) {

    private fun buildClient() = OkHttpClient.Builder()
        .build()

    private var baseUrl = "https://etc..." //default url

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(buildClient())
        .build()

    var retrofit: Retrofit = buildRetrofit()
        private set

    fun setUrl(url: String) {
        if (baseUrl != url)
            baseUrl = url

        retrofit = buildRetrofit()
    }}
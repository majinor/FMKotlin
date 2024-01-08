package com.daffamuhtar.fmkotlin.util

import com.daffamuhtar.fmkotlin.BuildConfig
import com.daffamuhtar.fmkotlin.app.Server
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DynamicRetrofit(private val gson: Gson) {


    private fun buildClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer " + Server.token
                    )
                    .build()
                chain.proceed(newRequest)
            })
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    private var baseUrl = "https://api-app-staging-v10.fleetify.id/" //default url

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
    }
}

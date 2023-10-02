package com.daffamuhtar.fmkotlin.app

import android.content.Context
import com.daffamuhtar.fmkotlin.constants.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiConfig {

    companion object {
        private val contexta: Context? = null
        var userId: String? = null
        var token: String? = null
        var companyType: String? = null

        fun getRetrofit(context: Context, apiVersion: String?): Retrofit? {
            Server.urlSample(context, apiVersion)
            checkId(context)
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer " + Server.token
                        )
                        .build()
                    chain.proceed(newRequest)
                })
                .addInterceptor(interceptor)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(Server.urlSample(context, apiVersion))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun checkId(context: Context) {
            val sharedpreferences =
                context.getSharedPreferences(Constants.my_shared_preferences, Context.MODE_PRIVATE)
            userId = sharedpreferences.getString(Constants.EXTRA_USERID, null)
            token = sharedpreferences.getString(Constants.EXTRA_TOKEN, null)
            companyType = sharedpreferences.getString(Constants.EXTRA_COMPANYTYPE, null)
        }
    }


}
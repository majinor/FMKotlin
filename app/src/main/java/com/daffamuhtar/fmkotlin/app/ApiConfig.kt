package com.daffamuhtar.fmkotlin.app

import android.content.Context
import android.content.SharedPreferences
import com.daffamuhtar.fmkotlin.constants.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiConfig : KoinComponent {

    val sharedPreferences: SharedPreferences by inject()

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
            userId = ApiConfig().sharedPreferences.getString(Constants.EXTRA_USERID, null)
            token = ApiConfig().sharedPreferences.getString(Constants.EXTRA_TOKEN, null)
            companyType = ApiConfig().sharedPreferences.getString(Constants.EXTRA_COMPANYTYPE, null)
        }
    }


}
package com.daffamuhtar.fmkotlin.di.module

import android.content.Context
import com.daffamuhtar.fmkotlin.BuildConfig
import com.daffamuhtar.fmkotlin.app.Server
import com.daffamuhtar.fmkotlin.data.api.ApiHelper
import com.daffamuhtar.fmkotlin.data.api.ApiHelperImpl
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.daffamuhtar.fmkotlin.util.NetworkHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//=======

val appModule = module {

    single { provideOkHttpClient() }
    single { provideRetrofit(get(), Server.URL1) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }

    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }

}


private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
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

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): RepairServices =
    retrofit.create(RepairServices::class.java)

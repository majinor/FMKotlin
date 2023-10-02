package com.daffamuhtar.fmkotlin.di.module

import com.daffamuhtar.fmkotlin.app.Server
import com.daffamuhtar.fmkotlin.services.AccountServices
import com.daffamuhtar.fmkotlin.services.RepairServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

}
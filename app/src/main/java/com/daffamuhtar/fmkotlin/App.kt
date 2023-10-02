package com.daffamuhtar.fmkotlin

import android.app.Application
import com.daffamuhtar.fmkotlin.di.module.appModule
import com.daffamuhtar.fmkotlin.di.module.repositoryModule
import com.daffamuhtar.fmkotlin.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}
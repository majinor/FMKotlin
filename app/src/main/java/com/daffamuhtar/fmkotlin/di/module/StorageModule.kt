package com.daffamuhtar.fmkotlin.di.module

import androidx.room.Room
import com.daffamuhtar.fmkotlin.appv2.data.local.MechanicDatabase
import com.daffamuhtar.fmkotlin.constants.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single <MechanicDatabase>{
        Room.databaseBuilder(androidContext(), MechanicDatabase::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

//    single {
//        get<MechanicDatabase>().dao
//    }
}
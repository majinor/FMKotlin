package com.daffamuhtar.fmkotlin.di.module

import com.daffamuhtar.fmkotlin.data.repository.MainRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        MainRepository(get())
    }
}
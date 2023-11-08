package com.daffamuhtar.fmkotlin.di.module

import com.daffamuhtar.fmkotlin.data.repository.RepairCheckRepository
import com.daffamuhtar.fmkotlin.data.repository.RepairOnNonperiodRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        RepairCheckRepository(get())
    }

    single {
        RepairOnNonperiodRepository(get())

    }


}
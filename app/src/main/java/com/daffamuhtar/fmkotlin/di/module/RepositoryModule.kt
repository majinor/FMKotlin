package com.daffamuhtar.fmkotlin.di.module

import com.daffamuhtar.fmkotlin.data.repository_old.RepairCheckRepository
import com.daffamuhtar.fmkotlin.data.repository_old.RepairOnNonperiodRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        RepairCheckRepository(get())
    }

    single {
        RepairOnNonperiodRepository(get())

    }


}
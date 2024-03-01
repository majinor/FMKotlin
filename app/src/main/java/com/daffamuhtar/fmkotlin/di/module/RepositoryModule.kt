package com.daffamuhtar.fmkotlin.di.module

import com.daffamuhtar.fmkotlin.appv4.RepairRepository4
import com.daffamuhtar.fmkotlin.appv4.RepairRepositoryImpl4
import com.daffamuhtar.fmkotlin.data.repository.RepairCheckRepository
import com.daffamuhtar.fmkotlin.data.repository.RepairOnNonperiodRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single {
        RepairCheckRepository(get())
    }

    single <RepairRepository4>{
        RepairRepositoryImpl4(get(named("paging_api_services")))

    }

    single {
        RepairOnNonperiodRepository(get())

    }



}
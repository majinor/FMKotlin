package com.daffamuhtar.fmkotlin.di.module

import com.daffamuhtar.fmkotlin.constants.ConstantsApp
import com.daffamuhtar.fmkotlin.ui.repair_check.RepairCheckViewModel
import com.daffamuhtar.fmkotlin.ui.repair_on.RepairOngoingAdhocViewCopyModel
import com.daffamuhtar.fmkotlin.ui.repair_on.RepairOngoingNonperiodViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        RepairCheckViewModel(
            get(),
            get(),
            get(named(ConstantsApp.BASE_URL_V1_0)),
            get(named(ConstantsApp.BASE_URL_V2_0)),
            get(named(ConstantsApp.BASE_URL_V2_0_REP)),
            get(named(ConstantsApp.BASE_URL2))
        )
    }

    viewModel {
        RepairOngoingNonperiodViewModel(
            get(),
            get(),
            get(named(ConstantsApp.BASE_URL_V1_0)),
            get(named(ConstantsApp.BASE_URL_V2_0)),
            get(named(ConstantsApp.BASE_URL_V2_0_REP)),
            get(named(ConstantsApp.BASE_URL2))
        )
    }

    viewModel {
        RepairOngoingAdhocViewCopyModel(
            get()
        )
    }


}
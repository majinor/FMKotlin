package com.daffamuhtar.fmkotlin.di.module

import com.daffamuhtar.fmkotlin.appv2.ui.RepairV2Activity
import com.daffamuhtar.fmkotlin.appv2.ui.RepairV2ViewModel
import com.daffamuhtar.fmkotlin.constants.ConstantsApp
import com.daffamuhtar.fmkotlin.ui.repair_check.RepairCheckViewModel
import com.daffamuhtar.fmkotlin.ui.repair_on.RepairOngoingNonperiodViewModel
import com.daffamuhtar.fmkotlin.ui.repair_ongoing.RepairOngoingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        RepairCheckViewModel(
            get(),
            get(),
            get()
        )
    }

    viewModel {
        RepairOngoingViewModel(
            get(),
            get()
        )
    }

    viewModel {
        RepairV2ViewModel(
            get()
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


}
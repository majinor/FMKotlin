package com.daffamuhtar.fmkotlin.di.module

import com.daffamuhtar.fmkotlin.ui.check.RepairCheckViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        RepairCheckViewModel(get(),get())
    }
}
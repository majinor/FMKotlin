package com.daffamuhtar.fmkotlin.util

import com.daffamuhtar.fmkotlin.domain.model.Repair

data class RepairStateList(
    val isLoading: Boolean = false,
    val coins: List<Repair> = emptyList(),
    val errorCode: Int = 0,
    val error: String = ""
)

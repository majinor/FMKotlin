package com.daffamuhtar.fmkotlin.data.repository

import com.daffamuhtar.fmkotlin.data.api.RepairApiHelper
import com.daffamuhtar.fmkotlin.data.api.RepairOnNonperiodApiHelper
import com.daffamuhtar.fmkotlin.services.RepairServices

class RepairRepository(private val repairApiHelper: RepairApiHelper) {
    suspend fun getRepair(
        services: RepairServices,
        userId: String,
        page: Int,
        perpage: Int
    ) = repairApiHelper.getRepair(services, userId, page, perpage)
}
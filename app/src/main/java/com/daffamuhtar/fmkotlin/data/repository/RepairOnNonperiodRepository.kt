package com.daffamuhtar.fmkotlin.data.repository

import com.daffamuhtar.fmkotlin.data.api.RepairOnNonperiodApiHelper
import com.daffamuhtar.fmkotlin.services.RepairServices

class RepairOnNonperiodRepository (private val repairCheckApiHelper: RepairOnNonperiodApiHelper) {
    suspend fun getRepairOnNonperiod(services: RepairServices, userId: String) =  repairCheckApiHelper.getRepairOnNonperiod(services,userId)
}
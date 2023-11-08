package com.daffamuhtar.fmkotlin.data.repository

import com.daffamuhtar.fmkotlin.data.api.RepairCheckApiHelper
import com.daffamuhtar.fmkotlin.services.RepairServices

class RepairCheckRepository (private val repairCheckApiHelper: RepairCheckApiHelper) {
    suspend fun getCheckRepair(services: RepairServices, userId: String) =  repairCheckApiHelper.getCheckRepair(services,userId)
}
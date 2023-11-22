package com.daffamuhtar.fmkotlin.data.api

import com.daffamuhtar.fmkotlin.data.remote.response.RepairOnNonperiodResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import retrofit2.Response

interface RepairOnNonperiodApiHelper {
    suspend fun getRepairOnNonperiod(services: RepairServices, userId: String): Response<List<com.daffamuhtar.fmkotlin.data.remote.response.RepairOnNonperiodResponse>>
}
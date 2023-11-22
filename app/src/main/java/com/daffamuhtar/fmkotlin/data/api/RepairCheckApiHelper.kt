package com.daffamuhtar.fmkotlin.data.api

import com.daffamuhtar.fmkotlin.data.remote.response.RepairCheckResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import retrofit2.Response

interface RepairCheckApiHelper {
    suspend fun getCheckRepair(services: RepairServices, userId: String): Response<List<com.daffamuhtar.fmkotlin.data.remote.response.RepairCheckResponse>>
}
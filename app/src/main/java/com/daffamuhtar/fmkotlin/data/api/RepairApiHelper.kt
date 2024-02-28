package com.daffamuhtar.fmkotlin.data.api

import com.daffamuhtar.fmkotlin.data.response.RepairOnNonperiodResponse
import com.daffamuhtar.fmkotlin.data.response.RepairOngoingMetaDataResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import retrofit2.Response

interface RepairApiHelper {
    suspend fun getRepair(
        services: RepairServices,
        userId: String,
        page: Int,
        perpage: Int
    ): Response<RepairOngoingMetaDataResponse>
}
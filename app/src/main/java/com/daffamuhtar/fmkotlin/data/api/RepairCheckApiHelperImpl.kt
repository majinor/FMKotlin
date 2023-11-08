package com.daffamuhtar.fmkotlin.data.api

import com.daffamuhtar.fmkotlin.data.response.RepairCheckResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import retrofit2.Response

class RepairCheckApiHelperImpl() : RepairCheckApiHelper {

    override suspend fun getCheckRepair(services: RepairServices, userId: String): Response<List<RepairCheckResponse>> = services.getCheckRepair(userId,0)

}
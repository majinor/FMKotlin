package com.daffamuhtar.fmkotlin.data.api

import com.daffamuhtar.fmkotlin.data.response.RepairOnNonperiodResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import retrofit2.Response

class RepairOnNonperiodApiHelperImpl() : RepairOnNonperiodApiHelper {

    override suspend fun getRepairOnNonperiod(services: RepairServices,userId: String): Response<List<RepairOnNonperiodResponse>> = services.getRepairNonperiod(userId,0)

}
package com.daffamuhtar.fmkotlin.data.api

import com.daffamuhtar.fmkotlin.data.response.RepairOngoingMetaDataResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import retrofit2.Response

class RepairApiHelperImpl() : RepairApiHelper {

    override suspend fun getRepair(
        services: RepairServices,
        userId: String,
        page: Int,
        perpage: Int
    ): Response<RepairOngoingMetaDataResponse> = services.getRepairOngoingNew22(userId, null,"[18,19]",page,perpage)


}
package com.daffamuhtar.fmkotlin.data.api

import com.daffamuhtar.fmkotlin.data.response.RepairCheckResponse
import com.daffamuhtar.fmkotlin.services.RepairServices
import retrofit2.Response

class ApiHelperImpl(private val apiService: RepairServices) : ApiHelper {

    override suspend fun getCheckRepair(userId: String): Response<List<RepairCheckResponse>> = apiService.getCheckRepair(userId,0)

}
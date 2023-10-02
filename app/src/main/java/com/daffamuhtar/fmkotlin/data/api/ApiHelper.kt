package com.daffamuhtar.fmkotlin.data.api

import com.daffamuhtar.fmkotlin.data.response.RepairCheckResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getCheckRepair(userId: String): Response<List<RepairCheckResponse>>
}
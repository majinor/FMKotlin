package com.daffamuhtar.fmkotlin.appv2.api

import com.daffamuhtar.fmkotlin.data.response.RepairOnAdhocResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepairOrderServices {
    @GET("movie/popular")
    suspend fun getRepairOnAdhocList(@Query("page") page: Int): Response<RepairOnAdhocResponse>

}
package com.daffamuhtar.fmkotlin.appv2.data.remote

import com.daffamuhtar.fmkotlin.data.response.RepairOnAdhocResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RepairApiServices {

    @GET("api/mechanics/open_repair")
    suspend fun getRepairOnAdhocList(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("stageId") stageId: Int,
        @Query("page") page: Int,
        @Query("perpage") perpage: Int
    ): List<RepairOnAdhocResponse>

}
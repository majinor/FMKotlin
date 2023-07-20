package com.daffamuhtar.fmkotlin.services

import com.daffamuhtar.fmkotlin.model.Repair
import com.daffamuhtar.fmkotlin.model.response.CheckRepairResponse
import com.daffamuhtar.fmkotlin.model.response.RefreshTokenResponse
import com.daffamuhtar.fmkotlin.model.response.RepairProblemResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RepairServices {

    @GET("api/mechanics/open_wo")
    fun getCheckRepair(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("stageId") stageId: Int
    ): Call<List<CheckRepairResponse>>


    //detail

    @GET("api/mechanics/detail_wo")
    fun getRepairProblem(
        @Query("orderId") orderId: String
    ): Call<List<RepairProblemResponse>>

}
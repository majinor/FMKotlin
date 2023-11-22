package com.daffamuhtar.fmkotlin.services

import com.daffamuhtar.fmkotlin.data.remote.response.CallResponse
import com.daffamuhtar.fmkotlin.data.remote.response.TireConditionCategoryResponse
import com.daffamuhtar.fmkotlin.data.remote.response.TireInspectionItemResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface TireServices {

    @GET("api/Tires/tire_condition_category")
    fun getTireConditionCategory(
        @Query("vehicleId") vehicleId: String
    ): Call<List<com.daffamuhtar.fmkotlin.data.remote.response.TireConditionCategoryResponse>>

    @GET("api/tires/tires_inspection_history_by_vehicle_with_tire_position_id")
    fun getTireInspectionItemByVehicleWithTirePositionId(
        @Query("vehicleId") vehicleId: String,
        @Query("tirePositionId") tirePositionId: String,
    ): Call<List<com.daffamuhtar.fmkotlin.data.remote.response.TireConditionCategoryResponse>>


    @GET("api/mechanics/tire_inspection_history_by_spk_inspection")
    fun getTireInspectionItemResultOnRepair(
        @Query("spkId") spkId: String?,
        @Query("vehicleId") vehicleId: String?
    ): Call<List<com.daffamuhtar.fmkotlin.data.remote.response.TireInspectionItemResponse>>

    @Multipart
    @POST("api/Tires/inspection_from_mechanic_v2")
    fun postTireInspectionFromMechanicFromRepair(
        @Part("loggedMechanicId") loggedMechanicId: RequestBody?,
        @Part("spkId") spkId: RequestBody?,
        @Part("inspectionId") inspectionId: RequestBody?,
        @Part("vehicleId") powvehicleIdId: RequestBody?,
        @Part("odometer") odometer: RequestBody?,
        @Part("tireId") tireId: RequestBody?,
        @Part("tirePositionId") tirePositionId: RequestBody?,
        @Part("tireRTD1") tireRTD1: RequestBody?,
        @Part("tireRTD2") tireRTD2: RequestBody?,
        @Part("tireRTD3") tireRTD3: RequestBody?,
        @Part("tireRTD4") tireRTD4: RequestBody?,
        @Part("previousPsi") previousPsi: RequestBody?,
        @Part("latestPsi") latestPsi: RequestBody?,
        @Part("tireConditionCategory") powtireConditionCategoryId: RequestBody?,
        @Part("note") note: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<com.daffamuhtar.fmkotlin.data.remote.response.CallResponse>

}
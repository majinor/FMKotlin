package com.daffamuhtar.fmkotlin.services

import com.daffamuhtar.fmkotlin.data.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RepairServices {

    @GET("api/mechanics/open_wo")
    suspend fun getCheckRepair(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("stageId") stageId: Int
    ): Response<List<RepairCheckResponse>>

    @GET("api/mechanics/ongoing_order")
    fun getRepairOngoing(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("stageId") stageId: Int
    ): Call<List<RepairOnAdhocResponse>>

    @GET("api/mechanics/ongoing_order_v2")
    fun getRepairOngoingNew(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("orderType") orderType: String?,
        @Query("stageGroup") stageGroup: String,
        @Query("page") page: Int,
        @Query("perpage") perpage: Int,
    ): Call<RepairOngoingMetaDataResponse>

    @GET("api/mechanics/ongoing_order_v2")
    fun getRepairOngoingNew2(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("orderType") orderType: String?,
        @Query("stageGroup") stageGroup: String,
        @Query("page") page: Int,
        @Query("perpage") perpage: Int,
    ): Response<RepairOngoingMetaDataResponse>

    @GET("api/mechanics/open_repair")
    fun getRepairAdhoc(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("stageId") stageId: Int
    ): Call<List<RepairOnAdhocResponse>>

    @GET("api/mechanics/open_maintenance_order")
    fun getRepairPeriod(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("stageId") stageId: Int
    ): Call<List<RepairOnPeriodResponse>>

    @GET("api/mechanics/open_npm_wo")
    suspend fun getRepairNonperiod(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("stageId") stageId: Int
    ): Response<List<RepairOnNonperiodResponse>>

    @GET("api/mechanics/open_tire_wo")
    fun getRepairTire(
        @Query("loggedMechanicId") loggedMechanicId: String,
        @Query("stageId") stageId: Int
    ): Call<List<RepairOnTireResponse>>


    //detail

    //ADHOC ============================================

    @GET("api/mechanics/detail_wo")
    fun getRepairProblem(
        @Query("orderId") orderId: String?
    ): Call<List<RepairDetailProblemResponse>>


    @GET("api/orders/active_driver_by_order_id")
    fun getRepairActiveDriver(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailActiveDriverResponse>>

    @GET("api/orders/workshop_location")
    fun getRepairWorkshopInfo(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailWorkshopInfoResponse>>


    @GET("api/orders/mechanic_on_duty")
    fun getRepairMechanicInfo(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailMechanicInfoResponse>>

    @GET("api/mechanics/part_and_jasa")
    fun getRepairDetailPart(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailPartResponse>>

    @GET("api/mechanics/detail_assignment_sa")
    fun getRepairDetailNotes(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailNoteResponse>>


    @GET("api/mechanics/check_wo")
    fun getRepairDetailAfterCheck(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailAfterCheckResponse>>

    @GET("api/orders/part_list")
    fun getRepairDetailAfterRepair(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailAfterRepairResponse>>


    @GET("api/mechanics/inspection_pb")
    fun getRepairDetailAfterRepairInspection(
        @Query("orderId") orderId: String,
        @Query("spkId") spkId: String?,
    ): Call<List<RepairDetailAfterRepairInspectionResponse>>

    @GET("api/mechanics/uploaded_waste_photo")
    fun getRepairDetailAfterRepairWaste(
        @Query("spkId") spkId: String?
    ): Call<List<RepairDetailAfterRepairWasteResponse>>

    @GET("api/orders/uploaded_complain_photos")
    fun getRepairDetailAfterRepairComplain(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailAfterRepairComplainResponse>>

    //PERIOD ============================================

    @GET("api/mechanics/detail_wo")
    fun getRepairProblemPeriod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailProblemResponse>>


    @GET("api/maintenance_orders/active_driver_by_order_id")
    fun getRepairActiveDriverPeriod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailActiveDriverResponse>>

    @GET("api/maintenance_orders/workshop_location")
    fun getRepairWorkshopInfoPeriod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailWorkshopInfoResponse>>


    @GET("api/maintenance_orders/mechanic_on_duty")
    fun getRepairMechanicInfoPeriod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailMechanicInfoResponse>>

    @GET("api/mechanics/part_and_jasa_list")
    fun getRepairDetailPartPeriod(
        @Query("spkId") spkId: String?
    ): Call<List<RepairDetailPartResponse>>

    @GET("api/mechanics/note_maintenance_after_repair_from_mechanic")
    fun getRepairDetailNotesPeriod(
        @Query("spkId") spkId: String?
    ): Call<List<RepairDetailNoteResponse>>


    @GET("api/mechanics/check_wo")
    fun getRepairDetailAfterCheckPeriod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailAfterCheckResponse>>

    @GET("api/mechanics/maintenance_order_part_list")
    fun getRepairDetailAfterRepairPeriod(
        @Query("orderId") orderId: String,
        @Query("pbId") pbId: String
    ): Call<List<RepairDetailAfterRepairResponse>>

//    String url = Server.urlSample(context, ConstantaApp.BASE_URL_V1_0) + "api/mechanics/inspection_pb?spkId=" + sid + "&orderId=" + orderid;

    @GET("api/mechanics/uploaded_maintenance_waste_photo")
    fun getRepairDetailAfterRepairWastePeriod(
        @Query("spkId") spkId: String?
    ): Call<List<RepairDetailAfterRepairWasteResponse>>

    @GET("api/maintenance_orders/uploaded_complain_photos")
    fun getRepairDetailAfterRepairComplainPeriod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailAfterRepairComplainResponse>>

    //NON PERIOD ============================================

    @GET("api/mechanics/detail_wo_npm")
    fun getRepairProblemNonperiod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailProblemResponse>>


    @GET("api/non_periodic_maintenance_orders/active_driver_by_order_id")
    fun getRepairActiveDriverNonperiod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailActiveDriverResponse>>

    @GET("api/non_periodic_maintenance_orders/workshop_location")
    fun getRepairWorkshopInfoNonperiod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailWorkshopInfoResponse>>


    @GET("api/non_periodic_maintenance_orders/mechanic_on_duty")
    fun getRepairMechanicInfoNonperiod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailMechanicInfoResponse>>

    @GET("api/non_periodic_maintenance_orders/detail_part_and_jasa")
    fun getRepairDetailPartNonperiod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailPartResponse>>

    @GET("api/mechanics/note_npm_after_repair_from_mechanic")
    fun getRepairDetailNotesNonperiod(
        @Query("spkId") spkId: String?
    ): Call<List<RepairDetailNoteResponse>>


    @GET("api/mechanics/check_wo")
    fun getRepairDetailAfterCheckNonperiod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailAfterCheckResponse>>

    @GET("api/non_periodic_maintenance_orders/part_list")
    fun getRepairDetailAfterRepairNonperiod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailAfterRepairResponse>>

    @GET("api/mechanics/uploaded_npm_waste_photo")
    fun getRepairDetailAfterRepairWasteNonperiod(
        @Query("spkId") spkId: String?
    ): Call<List<RepairDetailAfterRepairWasteResponse>>

    @GET("api/non_periodic_maintenance_orders/uploaded_complain_photos")
    fun getRepairDetailAfterRepairComplainNonperiod(
        @Query("orderId") orderId: String
    ): Call<List<RepairDetailAfterRepairComplainResponse>>

    @Multipart
    @POST("api/mechanics/request_additional_part_adhoc")
    fun postAdditionalPartRequestAdhoc(
        @Part("loggedMechanicId") loggedMechanicId: RequestBody,
        @Part("orderId") orderId: RequestBody,
        @Part("additionalNote") additionalNote: RequestBody,
        @Part file1: MultipartBody,
        @Part file2: MultipartBody,
        @Part file3: MultipartBody,
    ): Call<CallResponse>

//    val requestFile = RequestBody.create("file".toMediaTypeOrNull(), file)
//    val filePart = MultipartBody.Part.createFormData("photo", file.name, requestFile)
//    val vehicleIdPart = vehicleId.toRequestBody("text/plain".toMediaTypeOrNull())
//    val userIdPart = userId.toRequestBody("text/plain".toMediaTypeOrNull())
//    val notePart = note.toRequestBody("text/plain".toMediaTypeOrNull())



}
package com.daffamuhtar.fmkotlin.model.response

import com.google.gson.annotations.SerializedName

data class RepairOngoingResponse(

    @SerializedName("orderId")
    val orderId: String,

    @SerializedName("SPKId")
    val spkId: String,

    @SerializedName("pbId")
    val pbId: String,

    @SerializedName("stageId")
    val stageId: String,

    @SerializedName("vehicleId")
    val vehicleId: String,

    @SerializedName("vehicleBrand")
    val vehicleBrand: String,

    @SerializedName("vehicleType")
    val vehicleType: String,

    @SerializedName("vehicleVarian")
    val vehicleVarian: String,

    @SerializedName("vehicleYear")
    val vehicleYear: String,

    @SerializedName("vehicleLicenseNumber")
    val vehicleLicenseNumber: String,

    @SerializedName("vehicleDistrict")
    val vehicleDistrict: String,

    @SerializedName("vehicleLambungId")
    val vehicleLambungId: String,

    @SerializedName("noteFromSA")
    val noteFromSA: String,

    @SerializedName("startAssignment")
    val startAssignment: String,

    @SerializedName("additionalPartNote")
    val additionalPartNote: String,

    @SerializedName("startRepairOdometer")
    val startRepairOdometer: String,

    @SerializedName("locationOption")
    val locationOption: String,

    @SerializedName("isStoring")
    val isStoring: String
)


package com.daffamuhtar.fmkotlin.data.remote.response

import com.daffamuhtar.fmkotlin.domain.model.Repair
import com.google.gson.annotations.SerializedName

data class RepairOnAdhocResponse(

    @SerializedName("orderId")
    val orderId: String,

    @SerializedName("SPKId")
    val spkId: String,

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

fun RepairOnAdhocResponse.toRepair(): Repair {
    return Repair(
        orderId = orderId,
        spkId = spkId,
        pbId = null,
        stageId = stageId,
        stageName = null,
        vehicleId = vehicleId,
        vehicleBrand = vehicleBrand,
        vehicleType = vehicleType,
        vehicleVarian = vehicleVarian,
        vehicleYear = vehicleYear,
        vehicleLicenseNumber = vehicleLicenseNumber,
        vehicleLambungId = vehicleLambungId,
        vehicleDistrict = vehicleDistrict,
        noteFromSA = noteFromSA,
        workshopName = null,
        workshopLocation = null,
        startAssignment = startAssignment,
        additionalPartNote = additionalPartNote,
        startRepairOdometer = startRepairOdometer,
        locationOption = locationOption,
        isStoring = isStoring,
        orderType = null,
        colorCode = null
    )
}
package com.daffamuhtar.fmkotlin.appv3

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class RepairResponse3 (
    @SerializedName("orderId")
    val orderId: String,

    @SerializedName("spkId")
    val spkId: String?,

    @SerializedName("pbId")
    val pbId: String?,

    @SerializedName("stageId")
    val stageId: Int,

    @SerializedName("stageName")
    val stageName: String?,

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

    @SerializedName("vehicleLambungId")
    val vehicleLambungId: String?,

    @SerializedName("vehicleDistrict")
    val vehicleDistrict: String,

    @SerializedName("noteFromSA")
    val noteFromSA: String?,

    @SerializedName("workshopName")
    val workshopName: String?,

    @SerializedName("workshopLocation")
    val workshopLocation: String?,

    @SerializedName("scheduledDate")
    val scheduledDate: String,

    @SerializedName("additionalPartNote")
    val additionalPartNote: String?,

    @SerializedName("startRepairOdometer")
    val startRepairOdometer: String?,

    @SerializedName("locationOption")
    val locationOption: String?,

    @SerializedName("isStoring")
    val isStoring: String?,

    @SerializedName("orderType")
    val orderType: String?,

    @SerializedName("colorCode")
    val colorCode: String?,
)
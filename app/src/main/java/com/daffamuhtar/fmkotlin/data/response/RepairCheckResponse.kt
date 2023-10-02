package com.daffamuhtar.fmkotlin.data.response

import com.google.gson.annotations.SerializedName

data class RepairCheckResponse(

    @SerializedName("orderId")
    val orderId: String,

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

    @SerializedName("locationOption")
    val locationOption: String,

    @SerializedName("isStoring")
    val isStoring: String
)


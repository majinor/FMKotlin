package com.daffamuhtar.fmkotlin.data.response

data class Data(
    val SPKId: String?,
    val isStoring: String,
    val latestStage: String,
    val locationOption: String,
    val noteFromSA: String,
    val orderId: String,
    val scheduledDate: String,
    val stageId: String,
    val vehicleBrand: String,
    val vehicleDistrict: String,
    val vehicleLambungId: String?,
    val vehicleLicenseNumber: String,
    val vehicleType: String,
    val vehicleVarian: String,
    val vehicleYear: String
)
package com.daffamuhtar.fmkotlin.data.model

data class Repair(
    val orderId: String,
    val spkId: String?,
    val pbId: String?,
    val stageId: String,
    val stageName: String?,
    val vehicleId: String,
    val vehicleBrand: String,
    val vehicleType: String,
    val vehicleVarian: String,
    val vehicleYear: String,
    val vehicleLicenseNumber: String,
    val vehicleLambungId: String?,
    val vehicleDistrict: String,
    val noteSA: String?,
    val workshopName: String?,
    val workshopLocation: String?,
    val startAssignment: String,
    val additionalPartNote: String?,
    val startRepairOdometer: String?,
    val locationOption: String?,
    val isStoring: String?,
    val orderType: String?,
    val colorCode: String?,

    )

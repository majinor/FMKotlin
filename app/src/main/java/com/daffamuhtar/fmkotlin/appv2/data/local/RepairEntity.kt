package com.daffamuhtar.fmkotlin.appv2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepairEntity(
    @PrimaryKey
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
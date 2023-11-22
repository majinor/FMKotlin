package com.daffamuhtar.fmkotlin.data.remote.trial

data class RepairOrdersItem(
    val orderId: String,
    val orderNote: String,
    val problemName: List<ProblemName>,
    val scheduledDate: String,
    val stageName: String,
    val totalAfterTax: String,
    val vehicleBrand: String,
    val vehicleDistrict: String,
    val vehicleLicenseNumber: String,
    val vehiclePhoto: String,
    val vehicleType: String,
    val vehicleVarian: String,
    val vehicleYear: String,
    val workshopArea: String,
    val workshopName: String
)
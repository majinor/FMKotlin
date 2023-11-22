package com.daffamuhtar.fmkotlin.data.remote.response

import com.google.gson.annotations.SerializedName

data class RepairDetailActiveDriverResponse(
    @SerializedName("driverId")
    val driverId: String?,

    @SerializedName("driverName")
    val driverName: String,

    @SerializedName("driverPhone")
    val driverPhone: String,

    @SerializedName("driverPhoto")
    val driverPhoto: String
)


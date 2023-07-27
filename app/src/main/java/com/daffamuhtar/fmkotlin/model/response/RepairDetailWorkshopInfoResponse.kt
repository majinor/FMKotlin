package com.daffamuhtar.fmkotlin.model.response

import com.google.gson.annotations.SerializedName

data class RepairDetailWorkshopInfoResponse(
    @SerializedName("workshopId")
    val workshopId: String,

    @SerializedName("workshopName")
    val workshopName: String,

    @SerializedName("locationAddress")
    val locationAddress: String,

    @SerializedName("locationUrl")
    val locationUrl: String

)

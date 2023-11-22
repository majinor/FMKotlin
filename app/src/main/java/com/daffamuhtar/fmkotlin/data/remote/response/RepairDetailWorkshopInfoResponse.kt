package com.daffamuhtar.fmkotlin.data.remote.response

import com.google.gson.annotations.SerializedName

data class RepairDetailWorkshopInfoResponse(
    @SerializedName("workshopId")
    val workshopId: String,

    @SerializedName("workshopName")
    val workshopName: String,

    @SerializedName("locationAddress")
    val locationAddress: String,

    @SerializedName("locationUrl")
    val locationUrl: String,

    @SerializedName("endAssignmentDate")
    val endAssignmentDate: String,

    @SerializedName("vehicleChassisTypeId")
    val vehicleChassisTypeId: String?,

    @SerializedName("isRequiredTireInspection")
    val isRequiredTireInspection: Boolean?,

)

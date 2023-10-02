package com.daffamuhtar.fmkotlin.data.response

import com.google.gson.annotations.SerializedName

data class RepairDetailMechanicInfoResponse(
    @SerializedName("mechanicId")
    val mechanicId: String,

    @SerializedName("mechanicName")
    val mechanicName: String,

    @SerializedName("mechanicPhone")
    val mechanicPhone: String,

    @SerializedName("mechanicPhoto")
    val mechanicPhoto: String
)

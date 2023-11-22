package com.daffamuhtar.fmkotlin.data.remote.response

import com.google.gson.annotations.SerializedName

data class RepairDetailNoteResponse(
    @SerializedName("noteCheckFromMechanic")
    val noteCheckFromMechanic: String?,

    @SerializedName("saName")
    val saName: String,

    @SerializedName("noteAfterRepairFromMechanic", alternate = ["noteMaintenanceAfterRepairFromMechanic"])
    val noteAfterRepairFromMechanic: String?,

    @SerializedName("warehouseName")
    val warehouseName: String?,

)

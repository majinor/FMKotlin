package com.daffamuhtar.fmkotlin.data.remote.response

import com.google.gson.annotations.SerializedName

data class RepairDetailAfterRepairResponse(

    @SerializedName("partIdFromFleetify", alternate = ["partAndJasaMasterId"])
    val partIdFromFleetify: String,

    @SerializedName("partSKU", alternate = ["partAndJasaSKU"])
    val partSku: String,

    @SerializedName("partName")
    val partName: String,

    @SerializedName("totalUsedPart")
    val totalUsedPart: Int,

    @SerializedName("partQuantity")
    val partQuantity: Int,

    @SerializedName("realPartQuantity")
    val realPartQuantity: Double,

    @SerializedName("isRequiredScan")
    val isRequiredScan: Int,

    @SerializedName("usedPartType")
    val usedPartType: Int,

    @SerializedName("scanHistory")
    val isSkip: ArrayList<Boolean>,

    @SerializedName("2")
    val problemPhoto1: String?,

    @SerializedName("3")
    val problemPhoto2: String?,

    @SerializedName("4")
    val problemPhoto3: String?,

    @SerializedName("isAllowToSkip")
    val isAllowToSkip: Int,

    @SerializedName("tirePositionId")
    val tirePositionId: String?
)


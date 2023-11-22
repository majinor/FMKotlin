package com.daffamuhtar.fmkotlin.data.remote.response

import com.google.gson.annotations.SerializedName

data class RepairDetailAfterRepairInspectionResponse(

    @SerializedName("itemInspectionId")
    val itemInspectionId: String,

    @SerializedName("itemInspectionName")
    val itemInspectionName: String,

    @SerializedName("isRequired")
    val isRequired: Int,

    @SerializedName("file1")
    val problemPhoto1: String?,

    @SerializedName("file2")
    val problemPhoto2: String?,

    @SerializedName("file3")
    val problemPhoto3: String?,
)


package com.daffamuhtar.fmkotlin.data.response

import com.daffamuhtar.fmkotlin.data.TireConditionCategory
import com.google.gson.annotations.SerializedName

data class TireInspectionItemResponse(

    @SerializedName("inspectionId")
    var inspectionId: String,

    @SerializedName("createdAt")
    val tireInspectionDate: String,

    @SerializedName("mechanicName")
    val mechanicName: String,

    @SerializedName("tireId")
    val tireId: String,

    @SerializedName("tireName")
    val tireName: String,

    @SerializedName("tirePositionId")
    val tirePositionId: String,

    @SerializedName("tirePositionName")
    val tirePositionName: String,

    @SerializedName("tireRTD1")
    val tireRTD1: String,

    @SerializedName("tireRTD2")
    val tireRTD2: String,

    @SerializedName("tireRTD3")
    val tireRTD3: String,

    @SerializedName("tireRTD4")
    val tireRTD4: String,

    @SerializedName("latestPsi")
    val latestPsi: String,

    @SerializedName("previousPsi")
    val previousPsi: String,

    @SerializedName("odometer")
    val odometer: String,

    @SerializedName("tireConditionCategoryModels")
    val tireConditionCategoryModels: ArrayList<TireConditionCategory>,

    @SerializedName("tireConditionCategoryId")
    val tireConditionId : Int,

    @SerializedName("tireConditionCategoryName")
    val tireConditionCategory: String,

    @SerializedName("note")
    val note: String,

    @SerializedName("photo")
    val inspectionPhoto: String,

    )

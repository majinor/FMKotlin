package com.daffamuhtar.fmkotlin.data.model

data class RepairDetailAfterRepairTireInspection(

    val inspectionId: String,
    val tireInspectionDate: String,
    val mechanicName: String,
    val tireId: String,
    val tireName: String,
    val tirePositionId: String,
    val tirePositionName: String,
    val tireRTD1: String,
    val tireRTD2: String,
    val tireRTD3: String,
    val tireRTD4: String,
    val latestPsi: String,
    val previousPsi: String,
    val odometer: String,
    val tireConditionCategoryModels: ArrayList<TireConditionCategory>,
    val tireConditionId: Int,
    val tireConditionCategory: String,
    val note: String,
    val inspectionPhoto: String,
)
package com.daffamuhtar.fmkotlin.model

class RepairDetailAfterRepair(
    val partIdFromFleetify: String,
    val partSku: String,
    val partName: String,
    val totalUsedPart: Int,
    val partQuantity: Int,
    val realPartQuantity: Double,
    val isRequiredScan: Int,
    val usedPartType: Int,
    val isSkip: ArrayList<Boolean>,
    val photos: ArrayList<Photo>,
    val isAllowToSkip: Int,
    val tirePositionId: String?,
)
package com.daffamuhtar.fmkotlin.data.model

class RepairDetailAfterRepairInspection(
    val itemInspectionId: String,
    val itemInspectionName: String,
    val isRequired: Int,
    val photos: ArrayList<Photo>,
)
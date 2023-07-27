package com.daffamuhtar.fmkotlin.model

data class RepairDetailPart(
    val partSku: String,
    val partName: String,
    val partBrand: String?,
    val partQuantity: String,
    val partUnit: String?,
    val newPartId: String?,
    val itemPrice: String?,
    val totalPrice: String?
)

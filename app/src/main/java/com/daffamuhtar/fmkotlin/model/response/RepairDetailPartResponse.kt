package com.daffamuhtar.fmkotlin.model.response

import com.google.gson.annotations.SerializedName

data class RepairDetailPartResponse(
    @SerializedName("partSKU", alternate = ["itemSKU"])
    val partSku: String,

    @SerializedName("partName", alternate = ["itemName"])
    val partName: String,

    @SerializedName("partBrand")
    val partBrand: String?,

    @SerializedName("partQuantity", alternate = ["itemQuantity"])
    val partQuantity: String,

    @SerializedName("partUnit")
    val partUnit: String?,

    @SerializedName("itemPrice")
    val itemPrice: String?,

    @SerializedName("totalPrice")
    val totalPrice: String?,

    @SerializedName("newTireId")
    val newPartId: String?
)

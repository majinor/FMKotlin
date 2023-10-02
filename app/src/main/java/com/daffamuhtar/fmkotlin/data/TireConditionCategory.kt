package com.daffamuhtar.fmkotlin.data

import com.google.gson.annotations.SerializedName

data class TireConditionCategory(
    @SerializedName("tireConditionCategoryId")
    val tireConditionCategoryId: Int,

    @SerializedName("tireConditionCategoryName")
    val tireConditionCategoryName: String

)

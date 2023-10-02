package com.daffamuhtar.fmkotlin.data.response

import com.google.gson.annotations.SerializedName

data class TireConditionCategoryResponse(
    @SerializedName("tireConditionCategoryId")
    val tireConditionCategoryId: Int,

    @SerializedName("tireConditionCategoryName")
    val tireConditionCategoryName: String

)

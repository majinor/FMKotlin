package com.daffamuhtar.fmkotlin.model.response

import com.google.gson.annotations.SerializedName

data class RepairProblemPhotoResponse(
    @SerializedName("photo")
    var photo: String,

    @SerializedName("isEditable")
    var isEditable: Boolean
)
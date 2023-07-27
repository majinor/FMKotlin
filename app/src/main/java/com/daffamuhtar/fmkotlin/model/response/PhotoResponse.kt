package com.daffamuhtar.fmkotlin.model.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("photo")
    var photo: String,

    @SerializedName("isEditable")
    var isEditable: Boolean
)
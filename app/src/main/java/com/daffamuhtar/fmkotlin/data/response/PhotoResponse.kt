package com.daffamuhtar.fmkotlin.data.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("photo")
    var photo: String,

    @SerializedName("isEditable")
    var isEditable: Boolean
)
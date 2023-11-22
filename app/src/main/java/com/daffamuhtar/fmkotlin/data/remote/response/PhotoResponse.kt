package com.daffamuhtar.fmkotlin.data.remote.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("photo")
    var photo: String,

    @SerializedName("isEditable")
    var isEditable: Boolean
)
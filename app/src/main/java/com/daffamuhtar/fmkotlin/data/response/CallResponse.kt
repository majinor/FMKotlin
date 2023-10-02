package com.daffamuhtar.fmkotlin.data.response

import com.google.gson.annotations.SerializedName

data class CallResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("errorType")
    val code: Int,

    @SerializedName("errorType2")
    var errorType: String
)


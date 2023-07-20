package com.daffamuhtar.fmkotlin.model.response

import com.google.gson.annotations.SerializedName

data class RepairProblemResponse(

    @SerializedName("problemId")
    var problemId: String,

    @SerializedName("problemDetail")
    val problemNote: String,

    @SerializedName("1")
    val problemPhoto1: String,

    @SerializedName("2")
    val problemPhoto2: String,

    @SerializedName("3")
    val problemPhoto3: String
)


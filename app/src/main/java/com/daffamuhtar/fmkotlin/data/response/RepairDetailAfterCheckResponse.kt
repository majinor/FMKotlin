package com.daffamuhtar.fmkotlin.data.response

import com.google.gson.annotations.SerializedName

data class RepairDetailAfterCheckResponse(

    @SerializedName("1")
    val problemPhoto1: String?,

    @SerializedName("2")
    val problemPhoto2: String?,

    @SerializedName("3")
    val problemPhoto3: String?,

    @SerializedName("4")
    val problemPhoto4: String?,

    @SerializedName("5")
    val problemPhoto5: String?,

    @SerializedName("6")
    val problemPhoto6: String?


)


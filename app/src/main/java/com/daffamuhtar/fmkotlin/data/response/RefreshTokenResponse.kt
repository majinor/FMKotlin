package com.daffamuhtar.fmkotlin.data.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("loggedUserId")
    val loggedUserId: String,

    @SerializedName("requiredUpdate")
    val requiredUpdate: String,

    @SerializedName("jwtToken")
    val token: String,

    @SerializedName("message")
    var message: String
)


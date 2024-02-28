package com.daffamuhtar.fmkotlin.data.request

import com.google.gson.annotations.SerializedName

data class RepairOngoingRequest(
    @SerializedName ("loggedMechanicId")
    val loggedMechanicId:String,

    @SerializedName ("orderType")
    val orderType:String?,

    @SerializedName ("orderType")
    val stageId:Int?,

    @SerializedName ("stageGroup")
    val stageGroup: IntArray,

    @SerializedName ("page")
    val page:String,

    @SerializedName ("perpage")
    val perpage:String,

    )

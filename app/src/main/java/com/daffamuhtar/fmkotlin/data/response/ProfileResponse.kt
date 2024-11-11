package com.daffamuhtar.fmkotlin.data.response

import com.google.gson.annotations.SerializedName

class ProfileResponse (

    @SerializedName("mechanicName")
    var mechanicName: String,

    @SerializedName("mechanicPhoto")
    var mechanicPhoto: String,

    @SerializedName("mechanicPhone")
    var mechanicPhone: String,

    @SerializedName("mechanicEmail")
    var mechanicEmail: String,

    @SerializedName("mechanicUsername")
    var mechanicUsername: String,

    @SerializedName("mechanicWorkshop")
    var mechanicWorkshop: String,

    @SerializedName("mechanicNIK")
    var mechanicNIK: String,

    @SerializedName("mechanicKTP")
    var mechanicKTP: String,

    @SerializedName("mechanicKTPPhoto")
    var mechanicKTPPhoto: String,

    @SerializedName("mechanicBirthDate")
    var mechanicBirthDate: String,

    @SerializedName("mechanicBirthPlace")
    var mechanicBirthPlace: String,

    @SerializedName("mechanicAddress")
    var mechanicAddress: String,

)
package com.daffamuhtar.fmkotlin.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepairOngoingMetaDataResponse (
    val data: List<RepairOnAdhocResponse>,
    val meta: Meta
) : Parcelable
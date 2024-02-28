package com.daffamuhtar.fmkotlin.appv2.data.remote

import com.daffamuhtar.fmkotlin.data.response.Meta
import com.daffamuhtar.fmkotlin.data.response.RepairOnAdhocResponse

data class RepairMetaDataResponse(
    val data: List<RepairResponse>,
    val meta: Meta
)

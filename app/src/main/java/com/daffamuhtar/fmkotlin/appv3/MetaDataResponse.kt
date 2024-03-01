package com.daffamuhtar.fmkotlin.appv3

import com.daffamuhtar.fmkotlin.appv2.data.remote.RepairResponse
import com.daffamuhtar.fmkotlin.data.response.Meta

data class MetaDataResponse<T>(
    val data: List<T>,
    val meta: Meta
)

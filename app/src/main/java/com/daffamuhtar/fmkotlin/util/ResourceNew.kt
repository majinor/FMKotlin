package com.daffamuhtar.fmkotlin.util

import com.daffamuhtar.fmkotlin.data.response.Meta
import com.daffamuhtar.fmkotlin.data.response.RepairOnAdhocResponse
import com.daffamuhtar.fmkotlin.data.response.RepairOngoingMetaDataResponse

sealed class ResourceNew {
    val data:RepairOngoingMetaDataResponse = RepairOngoingMetaDataResponse( listOf<RepairOnAdhocResponse>(),
        Meta("2024-10-10",1,10,10,"10",100)
    )
    class Failure : ResourceNew()
    class Loading : ResourceNew()
    class Success : ResourceNew()
}
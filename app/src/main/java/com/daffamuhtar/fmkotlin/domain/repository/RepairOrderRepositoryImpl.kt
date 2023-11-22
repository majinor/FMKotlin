package com.daffamuhtar.fmkotlin.domain.repository

import com.daffamuhtar.fmkotlin.data.remote.response.RepairOnAdhocResponse
import com.daffamuhtar.fmkotlin.domain.model.Repair
import com.daffamuhtar.fmkotlin.services.RepairServices

class RepairOrderRepositoryImpl constructor(
    private val api: RepairServices
) : RepairOrderRepository {

    override suspend fun getRepairOnAdhocs(
        userId: String,
        stageId: Int,
        searchQuery: String
    ): List<RepairOnAdhocResponse> {
        return api.getRepairAdhoc(userId, stageId)
    }

}
package com.daffamuhtar.fmkotlin.domain.repository

import com.daffamuhtar.fmkotlin.data.remote.response.RepairOnAdhocResponse
import com.daffamuhtar.fmkotlin.domain.model.Repair

interface RepairOrderRepository {

    suspend fun getRepairOnAdhocs(userId : String, stageId : Int, searchQuery :String): List<RepairOnAdhocResponse>

//    suspend fun getCoinById(coinId: String): CoinDetailDto
}
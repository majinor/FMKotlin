package com.daffamuhtar.fmkotlin.appv4

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.daffamuhtar.fmkotlin.services.RepairServices
import kotlinx.coroutines.flow.Flow


class RepairRepositoryImpl4 (
    private val repairServices: RepairServices
) : RepairRepository4 {
    override fun getRepairList(): Flow<PagingData<RepairResponse4>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = { RepairPagingDataSource(repairServices) }
        ).flow
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
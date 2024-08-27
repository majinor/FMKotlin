package com.daffamuhtar.fmkotlin.appv4

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.daffamuhtar.fmkotlin.services.RepairServices
import kotlinx.coroutines.flow.Flow


class RepairRepositoryImpl4 (
    private val repairServices: RepairServices
) : RepairRepository4 {
    override fun getRepairCheckAdhocList(): Flow<PagingData<RepairResponse4>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = { RepairPagingDataSource(repairServices,"adhoc","menu_repair_inspection",) }
        ).flow
    }

    override fun getRepairList(): Flow<PagingData<RepairResponse4>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = { RepairPagingDataSource(repairServices,"adhoc",null) }
        ).flow
    }

    override fun getRepairListPeriod(): Flow<PagingData<RepairResponse4>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = { RepairPagingDataSource(repairServices,"maintenance",null) }
        ).flow
    }

    override fun getRepairListNonperiod(): Flow<PagingData<RepairResponse4>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = { RepairPagingDataSource(repairServices,"npm",null) }
        ).flow
    }

    override fun getRepairListTire(): Flow<PagingData<RepairResponse4>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = { RepairPagingDataSource(repairServices,"tire",null) }
        ).flow
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
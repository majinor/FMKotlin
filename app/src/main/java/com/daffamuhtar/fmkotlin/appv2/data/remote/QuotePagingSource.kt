package com.daffamuhtar.fmkotlin.appv2.data.remote

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.daffamuhtar.fmkotlin.appv2.data.local.MechanicDatabase
import com.daffamuhtar.fmkotlin.appv2.data.local.RepairEntity
import com.daffamuhtar.fmkotlin.appv2.data.mapper.toRepairEntity
import java.lang.Exception

class QuotePagingSource(
    private val repairDb: MechanicDatabase,
    private val apiServices: RepairApiServices
) : PagingSource<Int, RepairEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepairEntity> {
        return try {
            val position = params.key ?: 1
            val response = apiServices.getRepairOnAdhocList(
                loggedMechanicId = "MEC-MBA-99",
                stageId = 18,
                page = position,
                perpage = 5
            )

            repairDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    repairDb.dao.clearAll()
                }
                val beerEntities = repairs.map { it.toRepairEntity() }
                repairDb.dao.upsertAll(beerEntities)
            }

            return LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.totalPages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
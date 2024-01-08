package com.daffamuhtar.fmkotlin.appv2.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.daffamuhtar.fmkotlin.appv2.data.local.MechanicDatabase
import com.daffamuhtar.fmkotlin.appv2.data.local.RepairEntity
import com.daffamuhtar.fmkotlin.appv2.data.mapper.toRepairEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RepairRemoteMediator(
    private val repairDb: MechanicDatabase,
    private val repairApiServices: RepairApiServices
): RemoteMediator<Int, RepairEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepairEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        (state.pages.size / state.config.pageSize) + 1
                    }
                }
            }

            val repairs = repairApiServices.getRepairOnAdhocList(
                loggedMechanicId = "MEC-MBA-99",
                stageId = 18,
                page = loadKey,
                pageCount = state.config.pageSize,

            )

            repairDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    repairDb.dao.clearAll()
                }
                val beerEntities = repairs.map { it.toRepairEntity() }
                repairDb.dao.upsertAll(beerEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = repairs.isEmpty()
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
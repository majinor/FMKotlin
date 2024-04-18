package com.daffamuhtar.fmkotlin.appv2.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.daffamuhtar.fmkotlin.appv2.data.local.FleetifyMechanicDatabase
import com.daffamuhtar.fmkotlin.appv2.data.local.RepairEntity
import com.daffamuhtar.fmkotlin.appv2.data.mapper.toRepairEntity
import com.daffamuhtar.fmkotlin.services.RepairServices
import retrofit2.HttpException
import java.io.IOException
var page : Int = 1

@OptIn(ExperimentalPagingApi::class)
class RepairRemoteMediator(
    private val fleetifyMechanicDatabase: FleetifyMechanicDatabase,
    private val repairServices: RepairServices
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
                        nextPage()
                    }
                }
            }

            val repairs = repairServices.getRepairOngoingNew2(
                loggedUserId = "MEC-MBA-99",
                userPosition = "Mechanic",
                orderType = null,
                stageGroupId = null,
                page = loadKey,
                perpage = 5

            )

            fleetifyMechanicDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    fleetifyMechanicDatabase.dao.clearAll()
                }
                val repairEntity = repairs.data.map { it.toRepairEntity() }
                fleetifyMechanicDatabase.dao.upsertAll(repairEntity)
            }

            MediatorResult.Success(
                endOfPaginationReached = repairs.data.isEmpty()
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun nextPage(): Int {
        page = page +1
        return page

    }
}
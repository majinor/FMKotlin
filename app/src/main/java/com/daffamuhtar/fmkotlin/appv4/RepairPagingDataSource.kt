package com.daffamuhtar.fmkotlin.appv4

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.daffamuhtar.fmkotlin.services.RepairServices

class RepairPagingDataSource(private val repairServices: RepairServices) :
    PagingSource<Int, RepairResponse4>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepairResponse4> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = repairServices.getRepairOngoingNew4(
                loggedUserId = "MEC-MBA-99",
                userPosition = "Mechanic",
                orderType = null,
                stageGroupId = null,
                page = page,
                perpage = 5,
            )
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.data.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, RepairResponse4>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}
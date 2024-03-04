package com.daffamuhtar.fmkotlin.appv4

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Oguz Sahin on 11/10/2021.
 */

interface RepairRepository4 {
    fun getRepairList(): Flow<PagingData<RepairResponse4>>
}
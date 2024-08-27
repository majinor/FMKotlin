package com.daffamuhtar.fmkotlin.appv4

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Oguz Sahin on 11/10/2021.
 */

interface RepairRepository4 {

    //Repair Check
    fun getRepairCheckAdhocList(): Flow<PagingData<RepairResponse4>>

    //Repair Task
    fun getRepairList(): Flow<PagingData<RepairResponse4>>
    fun getRepairListPeriod(): Flow<PagingData<RepairResponse4>>
    fun getRepairListNonperiod(): Flow<PagingData<RepairResponse4>>
    fun getRepairListTire(): Flow<PagingData<RepairResponse4>>

}
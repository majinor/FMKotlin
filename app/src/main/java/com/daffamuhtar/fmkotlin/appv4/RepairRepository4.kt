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
    fun getRepairListAdhoc(): Flow<PagingData<RepairResponse4>>
    fun getRepairListPeriod(): Flow<PagingData<RepairResponse4>>
    fun getRepairListNonperiod(): Flow<PagingData<RepairResponse4>>
    fun getRepairListTire(): Flow<PagingData<RepairResponse4>>

    //Repair On
    fun getRepairOnAdhoc(): Flow<PagingData<RepairResponse4>>
    fun getRepairOnPeriod(): Flow<PagingData<RepairResponse4>>
    fun getRepairOnNonperiod(): Flow<PagingData<RepairResponse4>>
    fun getRepairOnTire(): Flow<PagingData<RepairResponse4>>


}
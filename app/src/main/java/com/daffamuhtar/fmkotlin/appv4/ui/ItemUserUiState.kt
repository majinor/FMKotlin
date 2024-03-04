package com.daffamuhtar.fmkotlin.appv4.ui

import com.daffamuhtar.fmkotlin.appv4.RepairResponse4
import com.daffamuhtar.fmkotlin.appv4.common.BaseUiState

/**
 * Created by Oguz Sahin on 11/11/2021.
 */
data class UserItemUiState(private val repairResponse4: RepairResponse4) {

    fun getImageUrl() = repairResponse4.orderId

    fun getName() = "${repairResponse4.orderId} ${repairResponse4.spkId}"

    fun getPhone() = repairResponse4.scheduledDate

    fun getMail() = repairResponse4.workshopName

}
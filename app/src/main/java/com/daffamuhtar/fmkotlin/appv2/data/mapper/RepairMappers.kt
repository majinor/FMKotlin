package com.daffamuhtar.fmkotlin.appv2.data.mapper

import com.daffamuhtar.fmkotlin.appv2.domain.model.RepairModel
import com.daffamuhtar.fmkotlin.data.response.RepairOnAdhocResponse
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.daffamuhtar.fmkotlin.appv2.data.local.RepairEntity

fun RepairOnAdhocResponse.toRepairEntity(): RepairEntity {
    return RepairEntity(
        orderId = orderId,
        spkId = spkId,
        pbId = null,
        stageId = stageId,
        stageName = RepairHelper.getRepairStageName(stageId, null),
        vehicleId = vehicleId,
        vehicleBrand = vehicleBrand,
        vehicleType = vehicleType,
        vehicleVarian = vehicleVarian,
        vehicleYear = vehicleYear,
        vehicleLicenseNumber = vehicleLicenseNumber,
        vehicleLambungId = vehicleLambungId,
        vehicleDistrict = vehicleDistrict,
        noteSA = noteFromSA,
        workshopName = null,
        workshopLocation = null,
        startAssignment = startAssignment,
        additionalPartNote = additionalPartNote,
        startRepairOdometer = startRepairOdometer,
        locationOption = locationOption,
        isStoring = isStoring,
        orderType = RepairHelper.getRepairOrderType(orderId, isStoring),
        colorCode = null,
    )

}

fun RepairEntity.toRepair(): RepairModel {
    return RepairModel(
        orderId = orderId,
        spkId = spkId,
        pbId = pbId,
        stageId = stageId,
        stageName = stageName,
        vehicleId = vehicleId,
        vehicleBrand = vehicleBrand,
        vehicleType = vehicleType,
        vehicleVarian = vehicleVarian,
        vehicleYear = vehicleYear,
        vehicleLicenseNumber = vehicleLicenseNumber,
        vehicleLambungId = vehicleLambungId,
        vehicleDistrict = vehicleDistrict,
        noteSA = noteSA,
        workshopName = workshopName,
        workshopLocation = workshopLocation,
        startAssignment = startAssignment,
        additionalPartNote = additionalPartNote,
        startRepairOdometer = startRepairOdometer,
        locationOption = locationOption,
        isStoring = isStoring,
        orderType = orderType,
        colorCode = colorCode,
    )
}
package com.daffamuhtar.fmkotlin.appv2.data.mapper

import com.daffamuhtar.fmkotlin.appv4.model.RepairEntity
import com.daffamuhtar.fmkotlin.appv2.data.remote.RepairResponse
import com.daffamuhtar.fmkotlin.data.model.Repair


fun RepairResponse.toRepairEntity(): RepairEntity {
    return RepairEntity(
        id = id,
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
        noteFromSA = noteFromSA,
        workshopName = workshopName,
        workshopLocation = workshopLocation,
        scheduledDate = scheduledDate,
        additionalPartNote = additionalPartNote,
        startRepairOdometer = startRepairOdometer,
        locationOption = locationOption,
        isStoring = isStoring,
        orderType = orderType,
        colorCode = colorCode
    )
}

fun RepairEntity.toRepair(): Repair {
    return Repair(
//        id = id,
        orderId = orderId,
        spkId = spkId,
        pbId = pbId,
        stageId = stageId.toString(),
        stageName = stageName,
        vehicleId = vehicleId,
        vehicleBrand = vehicleBrand,
        vehicleType = vehicleType,
        vehicleVarian = vehicleVarian,
        vehicleYear = vehicleYear,
        vehicleLicenseNumber = vehicleLicenseNumber,
        vehicleLambungId = vehicleLambungId,
        vehicleDistrict = vehicleDistrict,
        noteSA = noteFromSA,
        workshopName = workshopName,
        workshopLocation = workshopLocation,
        startAssignment = scheduledDate,
        additionalPartNote = additionalPartNote,
        startRepairOdometer = startRepairOdometer.toString(),
        locationOption = locationOption,
        isStoring = isStoring,
        orderType = orderType,
        colorCode = colorCode
    )
}
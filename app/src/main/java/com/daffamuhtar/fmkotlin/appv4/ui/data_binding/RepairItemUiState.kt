package com.daffamuhtar.fmkotlin.appv4.ui.data_binding

import android.content.Context
import android.view.View
import com.daffamuhtar.fmkotlin.appv4.RepairResponse4
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.daffamuhtar.fmkotlin.util.VehicleHelper
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Created by Oguz Sahin on 11/11/2021.
 */
data class RepairItemUiState(private val repair: Repair, private val context: Context) {

    val locale = Locale("id")

    fun getRepairTitle() = RepairHelper.getRepairTitle(
        repair.orderId,
        repair.isStoring
    )

    fun getRepairIcon() =
        RepairHelper.getRepairIcon(
            repair.orderId,
            repair.isStoring
        )

    fun getRepairId() =

        RepairHelper.getRepairId(repair.orderId, repair.spkId)

    fun getRepairDate() =
        RepairHelper.getRepairDate(repair.startAssignment)

    fun getRepairStage() =
        RepairHelper.getRepairStageModel(
            context,
            repair.stageId.toInt(),
            repair.stageName
        )

    fun getVehicleName() = VehicleHelper.getVehicleName(
        repair.vehicleId,
        repair.vehicleBrand,
        repair.vehicleType,
        repair.vehicleVarian,
        repair.vehicleYear,
        repair.vehicleLicenseNumber
    )

    fun getVehicleDistrict() = repair.vehicleDistrict
    fun getVehicleLambungId() = repair.vehicleLambungId
    fun getLyWorkshopVisibility() = repair.workshopName != null
    fun getWorkshopName() = repair.workshopName
    fun getWorkshopLocation() = repair.workshopLocation
    fun getLyAssignmentNoteVisibility() = repair.noteSA != null
    fun getAssignmentNote() = repair.noteSA

//    view.root.setOnClickListener
//    {
////                onItemClickCallback.onItemClicked(repair, position)
//    }


}
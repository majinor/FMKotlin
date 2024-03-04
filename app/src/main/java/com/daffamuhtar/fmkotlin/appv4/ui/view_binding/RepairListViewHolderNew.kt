package com.daffamuhtar.fmkotlin.appv4.ui.view_binding

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.databinding.ItemRepairBinding
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.daffamuhtar.fmkotlin.util.VehicleHelper
import java.util.Locale


class RepairListViewHolderNew(
    val view: ItemRepairBinding
) : RecyclerView.ViewHolder(view.root) {

    fun bind(repair: Repair, position: Int) {
        with(view) {

            lyWorkshop.visibility = View.GONE
            lyAssignmentNote.visibility = View.GONE
            val locale = Locale("id")

            tvRepairTitle.text = RepairHelper.getRepairTitle(
                repair.orderId,
                repair.isStoring
            )
            ivRepairIcon.setBackgroundResource(
                RepairHelper.getRepairIcon(
                    repair.orderId,
                    repair.isStoring
                )
            )
            tvRepairId.text =
                RepairHelper.getRepairId(repair.orderId, repair.spkId)
            tvRepairDate.text =
                RepairHelper.getRepairDate(repair.startAssignment)
            RepairHelper.setRepairStage(
                view.root.context,
                repair.stageId.toInt(),
                repair.stageName,
                tvRepairStage,
                ivRepairStageIcon,
                lyRepairStage
            )

            tvVehicleName.text = VehicleHelper.getVehicleName(
                repair.vehicleId,
                repair.vehicleBrand,
                repair.vehicleType,
                repair.vehicleVarian,
                repair.vehicleYear,
                repair.vehicleLicenseNumber
            )

            repair.workshopName?.let {
                lyWorkshop.visibility = View.VISIBLE
                tvWorkshopName.text = repair.workshopName
                tvWowkshopAddress.text = repair.workshopLocation
            }

            repair.noteSA?.let {
                lyAssignmentNote.visibility = View.VISIBLE
                tvAssignmentNote.text = repair.noteSA
            }

            view.root.setOnClickListener {
//                onItemClickCallback.onItemClicked(repair, position)
            }
        }
    }
//    fun bind(path: String?) {
//        path?.let {
//            binding.ivMoviePoster.load("https://image.tmdb.org/t/p/w500/$it") {
//                crossfade(durationMillis = 2000)
//                transformations(RoundedCornersTransformation(12.5f))
//            }
//        }
//    }
}
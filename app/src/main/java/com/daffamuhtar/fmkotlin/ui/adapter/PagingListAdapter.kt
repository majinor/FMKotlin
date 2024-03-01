package com.daffamuhtar.fmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.databinding.ItemRepairBinding
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.daffamuhtar.fmkotlin.util.VehicleHelper
import java.util.Locale

class PagingListAdapter : PagingDataAdapter<Repair, MoviePosterViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePosterViewHolder {
        return MoviePosterViewHolder(
            ItemRepairBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviePosterViewHolder, position: Int) {
        holder.bind(getItem(position)!!,position)
    }
}

class MovieDiffCallBack : DiffUtil.ItemCallback<Repair>() {
    override fun areItemsTheSame(oldItem: Repair, newItem: Repair): Boolean {
        return oldItem.orderId == newItem.orderId
    }

    override fun areContentsTheSame(oldItem: Repair, newItem: Repair): Boolean {
        return oldItem == newItem
    }
}

class MoviePosterViewHolder(
    val view: ItemRepairBinding
) : RecyclerView.ViewHolder(view.root) {

    fun bind(repair: Repair, position: Int) {
        with(view) {

            lyWorkshop.visibility = android.view.View.GONE
            lyAssignmentNote.visibility = android.view.View.GONE
            val locale = Locale("id")

            tvRepairTitle.text = com.daffamuhtar.fmkotlin.util.RepairHelper.getRepairTitle(
                repair.orderId,
                repair.isStoring
            )
            ivRepairIcon.setBackgroundResource(
                com.daffamuhtar.fmkotlin.util.RepairHelper.getRepairIcon(
                    repair.orderId,
                    repair.isStoring
                )
            )
            tvRepairId.text =
                com.daffamuhtar.fmkotlin.util.RepairHelper.getRepairId(repair.orderId, repair.spkId)
            tvRepairDate.text =
                com.daffamuhtar.fmkotlin.util.RepairHelper.getRepairDate(repair.startAssignment)
            com.daffamuhtar.fmkotlin.util.RepairHelper.setRepairStage(
                view.root.context,
                repair.stageId.toInt(),
                repair.stageName,
                tvRepairStage,
                ivRepairStageIcon,
                lyRepairStage
            )

            tvVehicleName.text = com.daffamuhtar.fmkotlin.util.VehicleHelper.getVehicleName(
                repair.vehicleId,
                repair.vehicleBrand,
                repair.vehicleType,
                repair.vehicleVarian,
                repair.vehicleYear,
                repair.vehicleLicenseNumber
            )

            repair.workshopName?.let {
                lyWorkshop.visibility = android.view.View.VISIBLE
                tvWorkshopName.text = repair.workshopName
                tvWowkshopAddress.text = repair.workshopLocation
            }

            repair.noteSA?.let {
                lyAssignmentNote.visibility = android.view.View.VISIBLE
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
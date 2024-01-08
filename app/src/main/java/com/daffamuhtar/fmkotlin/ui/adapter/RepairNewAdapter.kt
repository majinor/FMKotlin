package com.daffamuhtar.fmkotlin.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.size.Scale
import com.daffamuhtar.fmkotlin.appv2.domain.model.RepairModel
import com.daffamuhtar.fmkotlin.databinding.ItemRepairBinding
import com.daffamuhtar.fmkotlin.data.Repair
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.daffamuhtar.fmkotlin.util.RepairHelper.Companion.getRepairDate
import com.daffamuhtar.fmkotlin.util.RepairHelper.Companion.getRepairIcon
import com.daffamuhtar.fmkotlin.util.RepairHelper.Companion.getRepairId
import com.daffamuhtar.fmkotlin.util.RepairHelper.Companion.getRepairTitle
import com.daffamuhtar.fmkotlin.util.VehicleHelper.Companion.getVehicleName
import java.util.*

class RepairNewAdapter() : PagingDataAdapter<RepairModel, RepairNewAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ItemRepairBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRepairBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: RepairModel) {
            binding.apply {
                lyWorkshop.visibility = View.GONE
                lyAssignmentNote.visibility = View.GONE
                val locale = Locale("id")

                tvRepairTitle.text = getRepairTitle(item.orderId, item.isStoring)
                ivRepairIcon.setBackgroundResource(getRepairIcon(item.orderId, item.isStoring))
                tvRepairId.text = getRepairId(item.orderId, item.spkId)
                tvRepairDate.text = getRepairDate(item.startAssignment)
                RepairHelper.setRepairStage(
                    root.context,
                    item.stageId.toInt(),
                    item.stageName,
                    tvRepairStage,
                    ivRepairStageIcon,
                    lyRepairStage
                )

                tvVehicleName.text = getVehicleName(
                    item.vehicleId,
                    item.vehicleBrand,
                    item.vehicleType,
                    item.vehicleVarian,
                    item.vehicleYear,
                    item.vehicleLicenseNumber
                )

                item.workshopName?.let {
                    lyWorkshop.visibility = View.VISIBLE
                    tvWorkshopName.text = item.workshopName
                    tvWowkshopAddress.text = item.workshopLocation
                }

                item.noteSA?.let {
                    lyAssignmentNote.visibility = View.VISIBLE
                    tvAssignmentNote.text = item.noteSA
                }

                root.setOnClickListener {
                    onItemClickListener?.let {(it(item))

                    }
                }
//                root.setOnClickListener {
//                    onItemClickCallback.onItemClicked(item, position)
//                }
            }
        }
    }

    private var onItemClickListener: ((RepairModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (RepairModel) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<RepairModel>() {
            override fun areItemsTheSame(oldItem: RepairModel, newItem: RepairModel): Boolean {
                return oldItem.orderId == oldItem.orderId
            }

            override fun areContentsTheSame(oldItem: RepairModel, newItem: RepairModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}
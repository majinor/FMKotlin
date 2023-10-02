package com.daffamuhtar.fmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.databinding.ItemRepairDetailAfterRepairInspectionBinding
import com.daffamuhtar.fmkotlin.data.RepairDetailAfterRepairInspection
import com.daffamuhtar.fmkotlin.util.RepairHelper

class RepairDetailAfterRepairInspectionAdapter() :
    RecyclerView.Adapter<RepairDetailAfterRepairInspectionAdapter.ItemViewHolder>() {

    private var items = listOf<RepairDetailAfterRepairInspection>()
    private var repairStageId: Int = 0
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItems(items: List<RepairDetailAfterRepairInspection>) {
        items.let {
            this.items = it
            notifyDataSetChanged()
        }
    }

    fun setRepairStageId(it: Int) {
        it.let {
            this.repairStageId = it
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: RepairDetailAfterRepairInspection, position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ItemViewHolder(private val view: ItemRepairDetailAfterRepairInspectionBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: RepairDetailAfterRepairInspection, position: Int) {
            with(view) {
                tvItemName.text = item.itemInspectionName

                val photoAdapter = PhotoAdapter()
                photoAdapter.setItems(item.photos, 3)
                rvPhoto.adapter = photoAdapter

                val isEditable = (RepairHelper.isEdistable(
                    view.root.context,
                    repairStageId,
                    item.photos.size
                ))

                RepairHelper.setViewAfterRepairInspection(
                    view.root.context,
                    isEditable,
                    item.isRequired,
                    tvLabelOptional,
                )

                btnAddPhoto.visibility = if (isEditable) View.VISIBLE else View.GONE

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemRepairDetailAfterRepairInspectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], position)

//        if(!items[position].isActive) {
//            holder.itemView.setOnClickListener {
//                onItemClickCallback.onItemClicked(items[holder.adapterPosition], position)
//            }
//        }else{
//            holder.itemView.isClickable = false
//        }
    }

    override fun getItemCount(): Int = items.size
}
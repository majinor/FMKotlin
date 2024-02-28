package com.daffamuhtar.fmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.databinding.ItemRepairDetailPartBinding
import com.daffamuhtar.fmkotlin.data.model.RepairDetailPart

class RepairDetailPartAdapter() : RecyclerView.Adapter<RepairDetailPartAdapter.ItemViewHolder>() {

    private var items = listOf<RepairDetailPart>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItems(items: List<RepairDetailPart>) {
        items.let {
            this.items = it
            notifyDataSetChanged()
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: RepairDetailPart, position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ItemViewHolder(private val view: ItemRepairDetailPartBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: RepairDetailPart, position: Int) {
            with(view) {
                tvPartName.text = item.partName
                tvPartBrand.text = item.partBrand
                tvPartQuantity.text = item.partQuantity
                tvPartUnit.text = item.partUnit
                tvPartSku.text = item.partSku
                tvPartId.text = item.newPartId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemRepairDetailPartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position],position)

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
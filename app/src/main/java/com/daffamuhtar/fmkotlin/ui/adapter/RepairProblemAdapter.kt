package com.daffamuhtar.fmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.databinding.ItemRepairProblemBinding
import com.daffamuhtar.fmkotlin.model.ProblemRepair

class RepairProblemAdapter() : RecyclerView.Adapter<RepairProblemAdapter.ItemViewHolder>() {

    private var items = listOf<ProblemRepair>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItems(items: List<ProblemRepair>) {
        items.let {
            this.items = it
            notifyDataSetChanged()
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: ProblemRepair,position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ItemViewHolder(private val view: ItemRepairProblemBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: ProblemRepair, position: Int) {
            with(view) {
                if (position==0){
                    ivLineSeparator.visibility= View.GONE
                }
                val photoAdapter = PhotoAdapter()

                tvProblemNote.text = item.problemNote

                photoAdapter.setItems(item.problemPhotos)
                rvPhoto.adapter=photoAdapter

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemRepairProblemBinding.inflate(
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
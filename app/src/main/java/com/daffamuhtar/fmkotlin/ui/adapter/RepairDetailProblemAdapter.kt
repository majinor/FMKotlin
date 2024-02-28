package com.daffamuhtar.fmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.databinding.ItemRepairDetailProblemBinding
import com.daffamuhtar.fmkotlin.data.model.RepairDetailProblem

class RepairDetailProblemAdapter() : RecyclerView.Adapter<RepairDetailProblemAdapter.ItemViewHolder>() {

    private var items = listOf<RepairDetailProblem>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItems(items: List<RepairDetailProblem>) {
        items.let {
            this.items = it
            notifyDataSetChanged()
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: RepairDetailProblem, position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ItemViewHolder(private val view: ItemRepairDetailProblemBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: RepairDetailProblem, position: Int) {
            with(view) {
                if (position==0){
                    ivLineSeparator.visibility= View.GONE
                }
                val photoAdapter = PhotoAdapter()

                tvProblemNote.text = item.problemNote

                photoAdapter.setItems(item.problemPhotos, 3)
                rvPhoto.adapter=photoAdapter

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemRepairDetailProblemBinding.inflate(
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